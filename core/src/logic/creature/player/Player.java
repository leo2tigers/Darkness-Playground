/**
 * The Player class represents a player character controlled by a player.
 * 
 * @author 13thFloorGuy
 * @version 0.0
 * @since 12/04/2018
 */
package logic.creature.player;


import logic.creature.Creature;

public class Player extends Creature implements Controllable {
    public Gun gun;


    public Player(String name, int maxHealth, double positionX, double positionY, Gun gun) {
        super(name, maxHealth, positionX, positionY);
        this.setGun(gun);
        setupInputListener(this);
    }

    @Override
    public void move() {
        if (
                (key.left && key.right)
                || (!key.left && !key.right)
                || !movable
                || !isAlive()
        ) {
            if (speedY != 0) translate(0, speedY);
        }else {
            translate(speedX * orientation, speedY);
            if (key.up && !jumping) {
                jump();
            }else if (key.down && !jumping) {
                jump_down();
            }
        }
        
        nextKey();
    }

    @Override
    protected void attack_prepare() {
        if (attackable && gun.ammo != 0) {
        } else if (!gun.reloading) {
            attackable = false;
            gun.reload();
        } else {
        	gun.reload_interrupt();
        }
    }

    @Override
    protected void attackMethod() {
        gun.fire();
    }

	public void setGun(Gun gun) {
		this.gun = gun;
		gun.owner = this;
	}
}
