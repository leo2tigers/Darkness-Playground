package logic.creature;

import logic.GameMap;
import logic.URect;

public abstract class Creature {

    protected int health, maxHealth, armour = 0;
    protected int attackPower;
    protected int orientation = 1;
    protected URect hitBox;

    public final String name;
    public double positionX, positionY;
    public URect movementBox;
    public GameMap map;

    public Creature(String name, int maxHealth, int positionX, int positionY) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    protected int getHit(int damage) {
        int prevHealth = getHealth();
        setHealth(getHealth() - (damage - armour > 0 ? damage - armour : 0));
        return prevHealth - getHealth();
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setHealth(int health) {
        this.health = health > 0 ? (health < maxHealth ? health : maxHealth) : 0;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth > 1 ? maxHealth : 1;
    }

    public double[] translate(int x, int y) {
        //TODO translate()
        return new double[] {positionX, positionY};
    }

    public boolean isAlive() {return health > 0;}

    public void move(){
        //TODO move()
    }
    public void jump() {
        //TODO jump()
    }

    public abstract void attack();

    @Override
    public String toString() {
        return name + " ( " + (isAlive() ? "ALIVE" : "DEAD") +  ") , health = " + health + "/" + maxHealth;
    }
}
