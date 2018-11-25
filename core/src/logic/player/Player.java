package logic.player;

import log.Log;
import logic.URect;
import logic.creature.Creature;
import logic.creature.Monster;

public class Player extends Creature {

    private boolean left_KeyPressed, right_KeyPressed, up_KeyPressed, down_KeyPressed;
    private URect damageBox;


    public Player(String name, int maxHealth, int positionX, int positionY) {
        super(name, maxHealth, positionX, positionY);
    }

    public void KeyPressed(String key) {
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
        double orientation_coefficient = 100;
        double attackOffSetX = 0;
        double attackBoxWidth = 100;
        double attackOffSetY = 0;
        double attackBoxHeight = 100;

        damageBox = new URect(
                positionX + attackOffSetX + orientation_coefficient * orientation,
                positionY + attackOffSetY,
                attackBoxWidth,
                attackBoxHeight
        );
        return damageBox;
    }

    @Override
    protected void attackMethod() {
        for (Monster monster : map.monsters) {
            if (damageBox.overlap(monster.hitBox)) {
                Log.writeLog(monster.name + " is hit by " + damageBox);
                monster.getHit(attackPower);
            }
        }
    }
}
