package logic.creature.player;

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
        this.preDelay = 100;
        this.postDelay = 100;
    }

    @Override
    public void fire_method() {
        int damage = 1;
        owner.map.add(new Projectile(owner.positionX + PlayerStats.Pistol.RELATIVE_X, owner.positionY + PlayerStats.Pistol.RELATIVE_Y, 
        		                     /*width*/20, /*height*/20, 
        		                     owner.orientation, /*speed*/25, 
        		                     /*lifetime*/5000, damage, Projectile.TO_MONSTER, "Bullets/pistol_bullet.png"));
    }
}
