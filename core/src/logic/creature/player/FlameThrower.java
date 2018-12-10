package logic.creature.player;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import logic.GameObject;
import logic.URect;
import logic.creature.monster.Monster;

public class FlameThrower extends Gun {
	private URect damageBox;

	public FlameThrower() {
		super(/*type*/"Flame Thrower", /*max_ammo*/200, /*reload_time*/3, /*reload_interruptable*/false);
	}

	@Override
	public void fire_method() {
		int damage = 2;
		damageBox = new URect(owner.getX() + 100*owner.orientation, owner.getY(), 100, 100);
		for (GameObject gameObject : owner.map.getMonsters()) {
        	if (damageBox.overlap(((Monster) gameObject).hitBox)) {
        		((Monster) gameObject).getHit(damage);
        	}
        }
	}

	@Override
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
        this.preDelay = 0;
        this.postDelay = 0;
	}

	@Override
	public void render(ShapeRenderer shapeRenderer) {
		try {
			this.damageBox.shapeRender(shapeRenderer);
		} catch (NullPointerException npe) {}
	}

}
