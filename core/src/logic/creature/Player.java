package logic.creature;

import logic.URect;

public class Player extends Creature {

    private double attackBoxWidth, attackBoxHeight;
    private double attackOffSetX, attackOffSetY;
    private double orientation_coefficient;


    public Player(String name, int maxHealth, int positionX, int positionY) {
        super(name, maxHealth, positionX, positionY);
    }

    @Override
    public void attack() {
        URect damageBox = new URect(
                positionX + attackOffSetX + orientation_coefficient *orientation,
                positionY + attackOffSetY,
                attackBoxWidth,
                attackBoxHeight
        );

        for (Monster monster : map.monsters) {
            if (damageBox.overlap(monster.hitBox)) {
                monster.getHit(attackPower);
            }
        }
    }

}
