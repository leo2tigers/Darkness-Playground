package logic;

import java.util.Date;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.darknessplayground.game.screen.MainGame;

import logic.creature.monster.Monster;

public class Projectile extends GameObject {
	private double orientation;
    private double speed;
    private int damage;
    private URect damageBox;
    private final Date create_date = new Date();
    private final int lifetime;
	private int damage_check_type;
	private int hit = 0;
    public static final int TO_MONSTER = 0;
	public static final int TO_PLAYER = 1;
	
	private Texture img = null, hit_img = null;
	private String img_path = null, hit_path = null;

    public Projectile(double positionX, double positionY, 
    		          double width, double height, 
    		          double orientation, double speed, 
    		          int lifetime, int damage, int damage_check_type, String img_path) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.orientation = orientation;
        this.speed = speed;
        this.lifetime = lifetime;
        this.damage = damage;
        this.damage_check_type = damage_check_type;
        this.damageBox = new URect(positionX, positionY, width, height, Color.CORAL);
        this.img_path = img_path;
        setTexture();
    }
    public Projectile(double positionX, double positionY, 
			          double width, double height, 
			          double orientation, double speed, 
				      int lifetime, int damage, int damage_check_type, 
				      String img_path, String hit_path) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.orientation = orientation;
		this.speed = speed;
		this.lifetime = lifetime;
		this.damage = damage;
		this.damage_check_type = damage_check_type;
		this.damageBox = new URect(positionX, positionY, width, height, Color.CORAL);
		this.img_path = img_path;
		this.hit_path = hit_path;
		setTexture();
	}
    

	public void setTexture()  {
    	Gdx.app.postRunnable(new Runnable(){
            public void run(){
            	img = new Texture(img_path);
            	if (hit_path != null) {
            		hit_img = new Texture(hit_path);
            	}
            }
        });
    }
    
    @Override
    public void update() {
        positionX += speed*orientation;
        damageBox.translate(speed*orientation, 0.0);
        if (damage_check_type == TO_PLAYER) {
        	damage_to_player();
        } else if (damage_check_type == TO_MONSTER) {
        	damage_to_monster();
        }
        
        // check lifetime
        if ((new Date()).getTime() - create_date.getTime() >= lifetime) {
        	map.remove(this);
        }
    }
    
    private void damage_to_monster() {
    	Monster nearest = null;
        double distance = 720.0;
        for (Monster monster : map.getMonsters()) {
            if (damageBox.overlap(((Monster) monster).hitBox)) {
                if (getDistance(this, monster) <= distance) {
                    nearest = (Monster) monster;
                }
            }
        }
        if (nearest != null) {
            nearest.getHit(damage);
            hit += 1;
            map.remove(this);
        }
    }
    
    private void damage_to_player() {
    	if (this.damageBox.overlap(this.map.player.hitBox)) {
    		this.map.player.getHit(damage);
    		this.hit += 1;
    		this.img = this.hit_img;
    		this.speed = 0;
    	}
    }

	@Override
	public void shapeRender(ShapeRenderer shapeRenderer) {
		damageBox.shapeRender(shapeRenderer);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		try {
			batch.draw(img, (float) positionX, (float) positionY, (float) damageBox.width, (float) damageBox.height);
		} catch (NullPointerException npe) {
			MainGame.sendStatus("rendering delay");
		}
	}
	
	@Override
	public String toString() {
		return "Projectile ( " + (lifetime + this.create_date.getTime() - (new Date()).getTime()) + " ) " + 
	           ((this.damage_check_type == 0) ? "TO_MONSTER" : "TO_PLAYER") + 
	           " ( " + this.positionX + " , "  + this.positionY + " )";
	}
	
	public void dispose()
	{
		this.img.dispose();
	}
}
