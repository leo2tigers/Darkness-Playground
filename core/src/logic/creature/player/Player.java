<<<<<<< HEAD:core/src/logic/player/Player.java
/**
 * The Player class represents a player character controlled by a player.
 * 
 * @author 13thFloorGuy
 * @version 0.0
 * @since 12/04/2018
 */
=======
package logic.creature.player;
>>>>>>> master:core/src/logic/creature/player/Player.java

package logic.player;

import logic.Creature;

public class Player extends Creature implements Controllable {
    public Gun gun;


    public Player(String name, int maxHealth, double positionX, double positionY, Gun gun) {
        super(name, maxHealth, positionX, positionY);
        this.setGun(gun);
    }

    @Override
    public void keyPressed(String key) {
        if (key.equals("LEFT")) {
            orientation = -1;
            keyClass.left_KeyPressed = true;
        }
        if (key.equals("RIGHT")) {
            orientation = 1;
            keyClass.right_KeyPressed = true;
        }
        if (key.equals("UP")) {
        	keyClass.up_KeyPressed = true;
        }
        if (key.equals("DOWN")) {
        	keyClass.down_KeyPressed = true;
        }
        move();
    }

    @Override
    public void move() {
        if (
                (keyClass.left_KeyPressed && keyClass.right_KeyPressed)
                || (!keyClass.left_KeyPressed && !keyClass.right_KeyPressed)
                || !movable
                || !isAlive()
        ) {
            if (speedY != 0) translate(0, speedY);
        }else {
            translate(speedX * orientation, speedY);
            if (keyClass.up_KeyPressed && !jumping) {
                jump();
            }else if (keyClass.down_KeyPressed && !jumping) {
                jump_down();
            }
        }
        
        // reset all KeyPressed(s)
        keyClass.left_KeyPressed = false;
        keyClass.right_KeyPressed = false;
        keyClass.up_KeyPressed = false;
        keyClass.down_KeyPressed = false;
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
