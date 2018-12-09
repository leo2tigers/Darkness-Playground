package logic.creature.monster;

import java.util.Date;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import logic.GameMap;
import logic.GameProperties;
import logic.Meth;
import logic.URect;
import logic.creature.Creature;
import logic.creature.player.Player;

public abstract class Monster extends Creature{
	
	protected URect line_of_sight;
	protected double max_sight_range;
	protected double attack_range;
	protected int random_delay = 2000;
	private boolean walking = false;
	private Date last_random_date = new Date();
	
    public Monster(GameMap map, String name, int maxHealth, double positionX, double positionY, Texture img) {
        super(map, name, maxHealth, positionX, positionY, img);
    }

    @Override
    protected void attack_prepare() {
    }

    @Override
    protected void attackMethod() {

    }
    
    @Override
    public void getHit(int damage) {
        setHealth(getHealth() - (damage - armour > 0 ? damage - armour : 0));
        if(getHealth() <= 0)
        {
        	this.grantXp(this.map.player);
        	this.map.remove(this);
        }
    }
    
    abstract public void grantXp(Player player);
    
    public static Spawnable spawnable;
    
    public void update() {
    	before_update();
        if (jumping) {
            speedY -= GameProperties.Constant.GRAVITY;
        }
        move();
        this.speedX = 0;
        after_update();
    }

	protected void after_update() {
		this.setLoS();
	}
	protected void before_update() {
		if (this.line_of_sight == null) {
			this.setLoS();
		} else {
			if (this.map.player.hitBox.overlap(line_of_sight)) {
				inSight();
			}
			else {
				if ((new Date()).getTime() - this.last_random_date.getTime() >= this.random_delay) {
					double rand = Meth.random(0, 3);
					if (rand >= 2) {
						this.orientation = -1;
						this.speedX = this.orientation*this.movement_speed;
						this.walking = true;
					} else if (rand >= 1) {
						this.orientation = 1;
						this.speedX = this.orientation*this.movement_speed;
						this.walking = true;
					} else {
						this.walking = false;
					}
					this.last_random_date = new Date();
				} else {
					if (this.walking) {
						this.speedX = this.orientation*this.movement_speed;
					}
				}
			}
		}
	}
	
	protected abstract void inSight();

	protected void setLoS() {
		if (this.orientation == 1) {
			this.line_of_sight = new URect(this.positionX, this.positionY, max_sight_range, 100, Color.SALMON);
		} else if (this.orientation == -1) {
			this.line_of_sight = new URect(positionX - max_sight_range, positionY, max_sight_range, 100, Color.SALMON);
		}
	}
	
	@Override
    public void shapeRender(ShapeRenderer shapeRenderer) {
    	this.hitBox.shapeRender(shapeRenderer);
    	this.movementBox.shapeRender(shapeRenderer);
    	this.line_of_sight.shapeRender(shapeRenderer);
    }
}
