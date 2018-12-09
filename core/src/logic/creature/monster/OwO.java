package logic.creature.monster;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import logic.GameMap;
import logic.Meth;
import logic.SpawnPoint;
import logic.URect;
import logic.creature.player.Player;

public class OwO extends Monster {
	
	private URect damageBox = null;
	
	static String img_path_stand = "Monsters/Normal OwO/new_owo.png";
	static String img_path_jump = "Monsters/Normal OwO/new_owo_jump.png";
	
    public OwO(GameMap map, String name, double positionX, double positionY) {
        super(map, "OwO-" + name, 1, positionX, positionY, null);
        this.setHitBox(0, 0, 100, 100);
        this.setMovementBox(0, -5, 100, 10);
        setImg(img_path_stand);
        this.max_sight_range = 500;
        this.attack_range = 50;
        this.movement_speed = 1;
    }
    
    public OwO(GameMap map, String name,int maxHealth, double positionX, double positionY, Texture img, Texture atkImg, Texture jumpImg) {
        super(map, "OwO-" + name, maxHealth, positionX, positionY, img);
        this.setHitBox(0, 0, 100, 100);
        this.setMovementBox(0, -5, 100, 10);
        setImg(img_path_stand);
        this.max_sight_range = 500;
        this.attack_range = 50;
        this.movement_speed = 1;
    }
    
    @Override
    protected void attack_prepare() {
    	this.preDelay = 500;
    	this.postDelay = 500;
    }
    
    @Override
    protected void attackMethod() {
    	damageBox = new URect(positionX + this.hitBox.width/4 + this.orientation*50, positionY + this.hitBox.height/4, 
    			              50, 50, Color.CYAN);
    	if (damageBox.overlap(this.map.player.hitBox) && this.map.player.isAlive()) {
    		this.map.player.getHit(1);
    	}
    }

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(this.img, (float) positionX, (float)positionY);
	}
	
	@Override
	public void shapeRender(ShapeRenderer shapeRenderer) {
		super.shapeRender(shapeRenderer);
		if (this.damageBox != null) {
			this.damageBox.shapeRender(shapeRenderer);
		}
	}
	
	@Override
	public void grantXp(Player player)
	{
		player.addXp(30);
	}
	
	public static Spawnable spawnable = new Spawnable() {
		@Override
		public Monster spawn(SpawnPoint spawnPoint) {
			return new OwO(spawnPoint.map, "from_spawn_point_01", Meth.center_random(spawnPoint.getX(), spawnPoint.spawnWidth), spawnPoint.getY());
		}
	};

	@Override
	protected void inSight() {
		if (getDistance(this, this.map.player) <= attack_range) {
			attack();
		} else {
			if (this.map.player.getX() - this.positionX >= 0) {
				this.orientation = 1;
				this.speedX = this.orientation*this.movement_speed;
			} else {
				this.orientation = -1;
				this.movement_speed = this.orientation*this.movement_speed;
			}
		}
	}
}
