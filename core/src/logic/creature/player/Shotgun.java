package logic.creature.player;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.darknessplayground.game.screen.MainGame;

import logic.*;
import logic.creature.monster.Monster;
import logic.exceptions.NoAmmoException;

public class Shotgun extends Gun {
	URect firstBox, secondBox, thirdBox;
    public Shotgun() {
    	super("Shotgun", 5, 2000, true);
    	this.fireSound = Gdx.audio.newSound(Gdx.files.internal("Sfx/Shotgun_Fire.mp3"));
    }

    @Override
    public void fire_method() throws NoAmmoException {
    	if(this.ammo <= 0)
    	{
    		throw new NoAmmoException();
    	}
    	this.fireSound.play();
    	this.ammo -= 1;
        int damage = 1;
        firstBox = new URect((owner.orientation < 0) ? owner.getX() - 50 : owner.getX() + owner.hitBox.width, owner.getY() + owner.hitBox.height/2 - 25, 
        		             50, 50, Color.ORANGE);
        secondBox = new URect((owner.orientation < 0) ? owner.getX() - 100 : owner.getX() + owner.hitBox.width, owner.getY() + owner.hitBox.height/2 - 50, 
        		              100, 100, Color.ORANGE);
        thirdBox = new URect((owner.orientation < 0) ? owner.getX() - 200 : owner.getX() + owner.hitBox.width, owner.getY() + owner.hitBox.height/2 - 100, 
        		             200, 200, Color.ORANGE);
        ArrayList<Monster> overlapped_monster = new ArrayList<>();
        for (GameObject gameObject : owner.map.getMonsters()) {
        	if (firstBox.overlap(((Monster) gameObject).hitBox)) {
        		overlapped_monster.add((Monster) gameObject);
        	} else if (secondBox.overlap(((Monster) gameObject).hitBox)) {
        		overlapped_monster.add((Monster) gameObject);
        	} else if (thirdBox.overlap(((Monster) gameObject).hitBox)) {
        		overlapped_monster.add((Monster) gameObject);
        	}
        }
        if (!overlapped_monster.isEmpty()) {
	        Monster hit_monster = overlapped_monster.get(0);
	        double distance = Math.abs(overlapped_monster.get(0).getX() - owner.getX());
	        for (Monster monster : overlapped_monster) {
	        	if (Math.abs(monster.getX() - owner.getX()) <= distance) {
	        		hit_monster = monster;
	        	}
	        }
	        if (hit_monster != null) {
	        	if (firstBox.overlap(hit_monster.hitBox)) {
	        		hit_monster.getHit(damage);
        		}
	        	if (secondBox.overlap(hit_monster.hitBox)) {
	        		hit_monster.getHit(damage);
        		}
	        	if (thirdBox.overlap(hit_monster.hitBox)) {
	        		hit_monster.getHit(damage);
        		}
	        }
        }
    }

	@Override
	protected void createReloadThread() {
    	reloadThread = new Thread(() -> {
    		int reload_time_per_ammo = reload_time/max_ammo;
            reloading = true;
            int ammo_left = ammo;
            for (int i = 0; i < max_ammo - ammo_left; ++i) {
                try {
                    Thread.sleep(reload_time_per_ammo);
                    ++ammo;
                } catch (InterruptedException e) {
                	break;
                }
            }
            reloading =false;
        });
        this.preDelay = 500;
        this.postDelay = 500;
	}

	@Override
	public void render(ShapeRenderer shapeRenderer) {
		try {
			this.firstBox.shapeRender(shapeRenderer);
			this.secondBox.shapeRender(shapeRenderer);
			this.thirdBox.shapeRender(shapeRenderer);
		} catch (NullPointerException npe) {
		}
	}
}
