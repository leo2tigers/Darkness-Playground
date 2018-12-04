package logic.player;

import logic.Projectile;

public class Pistol extends Gun {
    public Pistol() {
        type = "Pistol";
        max_ammo = 7;
        ammo = max_ammo;
    }

    /**
     * This method is used to reload pistol's ammo one by one.
     * When reloading the pistol, the owner can't attack.
     * @return nothing
     */
    @Override
    public void reload() {
        final int reload_time_per_ammo = reload_time/ammo;
        (new Thread(() -> {
            enable = false;
            owner.attackable = false;
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

    /** 
     * This method is used to create an object represents pistol's bullet 
     * with 10 pixel width, 10 pixel height and speed of 25 pixel per frame.
     * @return nothing
     */
    @Override
    public void fire() {
        int damage = 1;
        owner.map.add(
                new Projectile(owner.positionX, owner.positionY, 10, 10, owner.orientation, 25, damage)
        );
    }
}
