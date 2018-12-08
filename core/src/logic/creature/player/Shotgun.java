package logic.creature.player;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

import logic.*;
import logic.creature.monster.Monster;

public class Shotgun extends Gun {
    public Shotgun() {
    	super("Shotgun", 5, 2000, true);
    	this.fireSound = Gdx.audio.newSound(Gdx.files.internal("Sfx/Shotgun_Fire.mp3"));
    	reloadThread = new Thread(() -> {
    		int reload_time_per_ammo = reload_time/ammo;
            reloading = true;
            owner.attackable = false;
            for (int i = 0; i < max_ammo - ammo; ++i) {
                try {
                    Thread.sleep(reload_time_per_ammo);
                    ++ammo;
                } catch (InterruptedException e) {
                	break;
                }
            }
            reloading =false;
            owner.attackable = true;
        });
        this.preDelay = 500;
        this.postDelay = 500;
    }

    @Override
    public void fire_method() {
    	this.fireSound.play();
        int damage = 1;
        URect firstBox = new URect(owner.positionX + 50*owner.orientation, owner.positionY, 25, 25);
        URect secondBox = new URect(owner.positionX + 75*owner.orientation, owner.positionY, 50, 50);
        URect thirdBox = new URect(owner.positionX + 100*owner.orientation, owner.positionY, 100, 100);
        ArrayList<Monster> overlapped_monster = new ArrayList<>();
        for (GameObject gameObject : owner.map.gameObjects) {
        	if (gameObject instanceof Monster) {
        		if (firstBox.overlap(((Monster) gameObject).hitBox)) {
        			overlapped_monster.add((Monster) gameObject);
        		}else if (secondBox.overlap(((Monster) gameObject).hitBox)) {
        			overlapped_monster.add((Monster) gameObject);
        		}else if (thirdBox.overlap(((Monster) gameObject).hitBox)) {
        			overlapped_monster.add((Monster) gameObject);
        		}
        	}
        }
        if (!overlapped_monster.isEmpty()) {
	        Monster hit_monster = overlapped_monster.get(0);
	        double distance = Math.abs(overlapped_monster.get(0).positionX - owner.positionX);
	        for (Monster monster : overlapped_monster) {
	        	if (Math.abs(monster.positionX - owner.positionX) <= distance) {
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
}
