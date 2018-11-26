package logic.creature;

import log.Log;

public class Monster extends Creature {

    public Monster(String name, int maxHealth, double positionX, double positionY) {
        super(name, maxHealth, positionX, positionY);
    }

    @Override
    protected Object attack_prepare() {
        return null;
    }

    @Override
    protected void attackMethod() {

    }
}
