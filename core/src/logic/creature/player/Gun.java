package logic.creature.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import logic.exceptions.NoAmmoException;

abstract public class Gun {
    Player owner;
    final String type;
    boolean enable, reloading;
    int max_ammo, ammo, reload_time;
    protected Thread reloadThread;
    private final boolean reload_interruptable;
	public int preDelay;
	public int postDelay;
	
	protected Sound reloadSound;
	protected Sound fireSound;
    
    Gun(String type, int max_ammo, int reload_time, boolean reload_interruptable) {
		super();
		this.type = type;
		this.max_ammo = max_ammo;
		this.ammo = this.max_ammo;
		this.reload_time = reload_time;
		this.reload_interruptable = reload_interruptable;
		this.enable = true;
		this.reloading = false;
		this.reloadSound = Gdx.audio.newSound(Gdx.files.internal("Sfx/Gun_reload.mp3"));
	}
    
	public void reload() {
		this.reloadSound.play();
		createReloadThread();
		reloadThread.start();
	}
	
	abstract protected void createReloadThread();
	
    public void fire() throws NoAmmoException {
    	if (ammo!= 0) {
    		fire_method();
    	}
    }

    abstract void fire_method() throws NoAmmoException;

	@Override
    public String toString() {
        return type + ": " + ammo + " / " + max_ammo;
    }

	public void reload_interrupt() {
		if (reload_interruptable) {
			reloadThread.interrupt();
			owner.attackable = true;
		}
	}
	
	public int getAmmo() {
		return ammo;
	}
	
	public int getMaxAmmo() {
		return this.max_ammo;
	}
	
	public boolean isReloading() {
		return this.reloading;
	}
	
	public void dispose()
	{
		this.reloadSound.dispose();
	}
}
