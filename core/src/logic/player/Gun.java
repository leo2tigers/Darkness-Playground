/**
 * This Gun class represents Player's Gun Weapon.
 */
package logic.player;

abstract public class Gun {
    Player owner;
    String type;
    boolean enable;
    int max_ammo, ammo, reload_time;
    abstract public void reload();
    abstract public void fire();

    @Override
    public String toString() {
        return type + ": " + ammo + " / " + max_ammo;
    }
}
