package logic.creature.player;

abstract public class Gun {
    Player owner;
    final String type;
    boolean enable, reloading;
    int max_ammo, ammo, reload_time;
    protected Thread reloadThread;
    private final boolean reload_interruptable;
	public int preDelay;
	public int postDelay;
    
    Gun(String type, int max_ammo, int reload_time, boolean reload_interruptable) {
		super();
		this.type = type;
		this.max_ammo = max_ammo;
		this.ammo = this.max_ammo;
		this.reload_time = reload_time;
		this.reload_interruptable = reload_interruptable;
		this.enable = true;
		this.reloading = false;
	}
    
	public void reload() {
		reloadThread.start();
	}
    abstract public void fire();

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
}
