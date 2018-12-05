package logic.creature.monster;

import com.badlogic.gdx.graphics.Texture;

import logic.creature.Creature;

public class Monster extends Creature {

    public Monster(String name, int maxHealth, double positionX, double positionY, Texture img) {
        super(name, maxHealth, positionX, positionY, img);
    }

    @Override
    protected void attack_prepare() {
    }

    @Override
    protected void attackMethod() {

    }
}
