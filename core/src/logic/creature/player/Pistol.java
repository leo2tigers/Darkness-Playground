package logic.creature.player;

import com.badlogic.gdx.Gdx;

import logic.Projectile;

public class Pistol extends Gun {

	public Pistol() {
        super("Pistol", 7, 1500, false);
        this.fireSound = Gdx.audio.newSound(Gdx.files.internal("Sfx/Piston_Fire.mp3"));
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
        this.preDelay = 300;
        this.postDelay = 500;
    }

    @Override
    public void fire_method() {
        int damage = 1;
        owner.map.add(new Projectile(owner.positionX + PlayerStats.Pistol.RELATIVE_X, owner.positionY + PlayerStats.Pistol.RELATIVE_Y, 
        		                     /*width*/20, /*height*/20, 
        		                     owner.orientation, /*speed*/25, 
        		                     /*lifetime*/1000, damage, Projectile.TO_MONSTER, "Bullets/pistol_bullet.png"));
        this.ammo -= 1;
    }
}
