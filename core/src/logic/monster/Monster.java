package logic.monster;

import logic.Creature;

public class Monster extends Creature {

    public Monster(String name, int maxHealth, double positionX, double positionY) {
        super(name, maxHealth, positionX, positionY);
    }

    @Override
    protected String attack_prepare() {
        return "";
    }

    @Override
    protected void attackMethod() {

    }
    
    @Override
    public String toString() {
    	return super.toString();
    }
}
