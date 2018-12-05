package logic.creature.player;

import logic.Projectile;

public class Pistol extends Gun {
    public Pistol() {
        type = "Pistol";
        max_ammo = 7;
        ammo = max_ammo;
    }

    @Override
    public void reload() {
        final int reload_time_per_ammo = reload_time/ammo;
        (new Thread(() -> {
            enable = false;
            for (int i = 0; i < max_ammo - ammo; ++i) {
                try {
                    Thread.sleep(reload_time_per_ammo);
                    ++ammo;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            enable = true;
            owner.attackable = true;
        })).start();
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
