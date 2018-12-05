package logic.creature.player;

import logic.creature.Creature;

public class Player extends Creature {

    private boolean left_KeyPressed, right_KeyPressed, up_KeyPressed, down_KeyPressed;
    public Gun gun;


    public Player(String name, int maxHealth, double positionX, double positionY, Gun gun) {
        super(name, maxHealth, positionX, positionY);
        this.setGun(gun);
    }

    public void keyPressed(String key) {
        if (key.equals("LEFT")) {
            orientation = -1;
            left_KeyPressed = true;
        }
        if (key.equals("RIGHT")) {
            orientation = 1;
            right_KeyPressed = true;
        }
        if (key.equals("UP")) {
            up_KeyPressed = true;
        }
        if (key.equals("DOWN")) {
            down_KeyPressed = true;
        }
        move();
    }

    @Override
    public void move() {
        if (
                (left_KeyPressed && right_KeyPressed)
                        || (!left_KeyPressed && !right_KeyPressed)
                        || !movable
                        || !isAlive()
        ) {
            if (speedY != 0) translate(0, speedY);
        }else {
            translate(speedX * orientation, speedY);
            if (up_KeyPressed && !jumping) {
                jump();
            }else if (down_KeyPressed && !jumping) {
                jump_down();
            }
        }
        left_KeyPressed = false;
        right_KeyPressed = false;
        up_KeyPressed = false;
        down_KeyPressed = false;
    }

    @Override
    protected String attack_prepare() {
        if (gun.ammo != 0) {
            return gun.type;
        } else {
            attackable = false;
            gun.reload();
            return gun.type + " reload";
        }
    }

    @Override
    protected void attackMethod() {
        gun.fire();
    }

	public void setGun(Gun gun) {
		this.gun = gun;
		this.gun.owner = this;
	}
}
