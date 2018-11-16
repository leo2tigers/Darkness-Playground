package logic;

abstract class Creature {

    final String name;
    protected int health, maxHealth;
    double positionX, positionY;
    protected GameMap map;

    public Creature(String name, int maxHealth, int positionX, int positionY) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.positionX = positionX;
        this.positionY = positionY;
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
        //TODO
        return new double[] {positionX, positionY};
    }

    public void moveLeft() {}
    public void moveRight() {}
    public void jump() {}
}
