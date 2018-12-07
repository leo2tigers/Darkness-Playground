package logic.creature.player;

import com.badlogic.gdx.graphics.Texture;

import logic.Projectile;

public class Pistol extends Gun {

	public Pistol() {
        super("Pistol", 7, 1500, false);
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
        this.preDelay = 500;
        this.postDelay = 500;
    }

    @Override
    public void fire() {
        int damage = 1;
        owner.map.add(new Projectile(owner.positionX, owner.positionY, 
        		                     /*width*/10, /*height*/10, 
        		                     owner.orientation, /*speed*/25, 
        		                     /*lifetime*/50, damage, Projectile.TO_MONSTER, new Texture("Bullets/pistol_bullet.png")));
    }
}
