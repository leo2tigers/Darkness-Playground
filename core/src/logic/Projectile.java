package logic;

import java.util.Date;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import logic.creature.monster.Monster;

public class Projectile extends GameObject {
	private double orientation;
    private double speed;
    private int damage;
    private URect damageBox;
    private final Date create_date = new Date();
    private final int lifetime;
	private int damage_check_type;
    public static final int TO_MONSTER = 0;
	public static final int TO_PLAYER = 1;
	
	private Texture img;

    public Projectile(double positionX, double positionY, 
    		          double width, double height, 
    		          double orientation, double speed, 
    		          int lifetime, int damage, int damage_check_type, Texture img) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.orientation = orientation;
        this.speed = speed;
        this.lifetime = lifetime;
        this.damage = damage;
        this.damage_check_type = damage_check_type;
        this.damageBox = new URect(positionX, positionY, width, height);
        this.img = img;
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
        for (GameObject monster : map.gameObjects) {
            if (monster instanceof Monster && damageBox.overlap(((Monster) monster).hitBox)) {
                if (getDistance(this, monster) <= distance) {
                    nearest = (Monster) monster;
                }
            }
        }
        if (nearest != null) {
            nearest.getHit(damage);
        }
    }
    
    private void damage_to_player() {
    	if (damageBox.overlap(map.player.hitBox)) {
    		map.player.getHit(damage);
    	}
    }

	@Override
	public void shapeRender(ShapeRenderer shapeRenderer) {
		damageBox.shapeRender(shapeRenderer);
	}
	
	public void dispose()
	{
		this.img.dispose();
	}
}
