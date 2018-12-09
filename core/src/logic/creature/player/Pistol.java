package logic.creature.player;

import com.badlogic.gdx.Gdx;

import logic.Projectile;
import logic.exceptions.NoAmmoException;

public class Pistol extends Gun {

	public Pistol() {
        super("Pistol", 7, 1500, false);
        this.fireSound = Gdx.audio.newSound(Gdx.files.internal("Sfx/Pistol_Fire.mp3"));
    }
	
	protected void createReloadThread() {
		reloadThread = new Thread(() -> {
            reloading = true;
            owner.attackable = false;
            try {
				Thread.sleep(reload_time);
				ammo = max_ammo;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            reloading = false;
            owner.attackable = true;
        });
        this.preDelay = 1000;
        this.postDelay = 500;
	}

    @Override
    public void fire_method() throws NoAmmoException {
    	if(this.ammo <= 0) {
    		throw new NoAmmoException();
    	}
    	this.fireSound.play();
        int damage = 1;
        int lifetime = 200;
        double width = 20;
        double height = 10;
        double speed = 25;
        if(owner.orientation == -1)
        {
        	owner.map.add(new Projectile(owner.getX() + PlayerStats.Pistol.RELATIVE_X, owner.getY() + PlayerStats.Pistol.RELATIVE_Y, 
        		                     	 width, height, 
        		                     	 owner.orientation, speed, 
        		                     	 lifetime, damage, Projectile.TO_MONSTER, "Bullets/pistol_bullet.png"));
        }
        else if(owner.orientation == 1)
        {
        	owner.map.add(new Projectile(owner.getX() + 115 - PlayerStats.Pistol.RELATIVE_X, owner.getY() + PlayerStats.Pistol.RELATIVE_Y,
        								 width, height,
        								 owner.orientation, speed,
        								 lifetime, damage, Projectile.TO_MONSTER, "Bullets/pistol_bullet.png"));
        }
        this.ammo -= 1;
    }
}
