package logic;

public class Creature {

    protected int health, maxHealth;
    double positionX, positionY;
    protected GameMap map;

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
}
