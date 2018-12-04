package logic.player;

import log.Log;
import logic.URect;
import logic.creature.Creature;
import logic.creature.Monster;

public class Player extends Creature {

    private boolean left_KeyPressed, right_KeyPressed, up_KeyPressed, down_KeyPressed;
    private URect damageBox;
    private Gun gun;


    public Player(String name, int maxHealth, int positionX, int positionY, Gun gun) {
        super(name, maxHealth, positionX, positionY);
        this.gun = gun;
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
            if (speedY != 0) Log.writeLog(name + " move 0 , " + speedY);
            translate(0, speedY);
        }else {
            Log.writeLog(name + " move " + speedX*orientation + " , " + speedY);
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
    protected Object attack_prepare() {
        return null;
    }

    @Override
    protected void attackMethod() {
        gun.fire();
    }
}
