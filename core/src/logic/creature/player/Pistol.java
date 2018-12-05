package logic.creature.player;

import logic.Projectile;

public class Pistol extends Gun {

	public Pistol(Player owner) {
        super(owner, "Pistol", 7, 1500, false);
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
    }

    @Override
    public void fire() {
        int damage = 1;
        owner.map.add(new Projectile(owner.positionX, owner.positionY, 
        		                     /*width*/10, /*height*/10, 
        		                     owner.orientation, /*speed*/25, 
        		                     /*lifetime*/50, damage, Projectile.TO_MONSTER));
    }
}
