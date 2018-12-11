package logic.creature.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import logic.Projectile;
import logic.exceptions.NoAmmoException;

public class Pistol extends Gun {

	public Pistol() {
		super("Pistol", 7, 1500, false);
		this.fireSound = Gdx.audio.newSound(Gdx.files.internal("Sfx/Pistol_Fire.mp3"));
	}

	protected void createReloadThread() {
		reloadThread = new Thread(new Runnable() {
			@Override
			public void run() {
				reloading = true;
				owner.setAttackable(false);
				try {
					Thread.sleep(reload_time);
					ammo = max_ammo;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				reloading = false;
				owner.setAttackable(true);
			}
		});
		this.preDelay = 1000;
		this.postDelay = 500;
	}

	@Override
	public void fire_method() throws NoAmmoException {
		if (this.ammo <= 0) {
			throw new NoAmmoException();
		}
		this.fireSound.play();
		int damage = 1;
		int lifetime = 200;
		double width = 20;
		double height = 10;
		double speed = 25;
		if (owner.getOrientation() == -1) {
			owner.getMap()
					.add(new Projectile(owner.getX() + PlayerStats.Pistol.RELATIVE_X,
							owner.getY() + PlayerStats.Pistol.RELATIVE_Y, width, height, owner.getOrientation(), speed,
							lifetime, damage, Projectile.TO_MONSTER, "Bullets/pistol_bullet.png"));
		} else if (owner.getOrientation() == 1) {
			owner.getMap()
					.add(new Projectile(owner.getX() + 115 - PlayerStats.Pistol.RELATIVE_X,
							owner.getY() + PlayerStats.Pistol.RELATIVE_Y, width, height, owner.getOrientation(), speed,
							lifetime, damage, Projectile.TO_MONSTER, "Bullets/pistol_bullet.png"));
		}
		this.ammo -= 1;
	}

	@Override
	public void render(ShapeRenderer shapeRenderer) {
	}
}
