package logic;

abstract class Creature {

    final String name;
    protected int health, maxHealth;
    double positionX, positionY;
    URect movementBox;
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
