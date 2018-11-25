package logic.creature;

import log.Log;
import logic.GameMap;
import logic.Tile;
import logic.URect;

import java.util.Date;

public abstract class Creature {

    private int health, maxHealth, armour = 0;
    protected int attackPower;
    protected int orientation = 1;

    public final String name ;
    protected double positionX, positionY;
    protected double speedX, speedY = 0;
    private double jumping_speed = 50;
    public URect hitBox, movementBox;
    public GameMap map;
    private Tile current_tile;
    protected boolean jumping = false, movable = true;
    private boolean attackable = true;
    private int preDelay = 100;
    private int animationTime = 200;
    private int postDelay = 100;
    private Date attackDate;

    public Creature(String name, int maxHealth, double positionX, double positionY) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void setHitBox(URect hitBox) {
        this.hitBox = hitBox;
    }

    public void setMovementBox(URect movementBox) {
        this.movementBox = movementBox;
        setCurrent_tile();
    }

    private void setCurrent_tile() {
        for (Tile tile : map.tiles) {
            if (movementBox.overlap(tile)) {
                this.current_tile = tile;
                break;
            }
        }
    }

    public void getHit(int damage) {
        int prevHealth = getHealth();
        setHealth(getHealth() - (damage - armour > 0 ? damage - armour : 0));
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    private void setHealth(int health) {
        this.health = health > 0 ? (health < maxHealth ? health : maxHealth) : 0;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth > 1 ? maxHealth : 1;
    }

    protected boolean isAlive() {return health > 0;}

    protected double[] translate(double x, double y) {
        double new_positionX = positionX + x;
        double new_positionY = positionY + y;

        URect check_movementBox = new URect(new_positionX, new_positionY, movementBox.width, movementBox.height);

        setCurrent_tile();
        if (check_movementBox.overlap(current_tile)) {
            new_positionY = current_tile.positionY + current_tile.height/2;
            jumping = false;
        }

        movementBox.translate(new_positionX - positionX, new_positionY - positionY);
        hitBox.translate(new_positionX - positionX, new_positionY - positionY);

        positionX = new_positionX;
        positionY = new_positionY;

        return new double[] {positionX, positionY};
    }


    public void move(){
        //TODO move()
    }
    protected void jump() {
        jumping = true;
        speedY = jumping_speed;
    }
    protected void jump_down() {
        jumping = true;
        speedY = -jumping_speed;
        translate(0., speedY);
    }

    @Override
    public String toString() {
        return name + " ( " + (isAlive() ? "ALIVE" : "DEAD") +  " ) , health = " + health + "/" + maxHealth;
    }

    public void update() {
        if (jumping) {
            double gravity = 10;
            speedY -= gravity;
        }
        move();
    }

    public void attack() {
        if (attackable && isAlive()) {

            Log.writeLog(name + " attack with " + attack_prepare());

            attackDate = new Date();
            Thread attackThread = new Thread() {
                @Override
                public void run() {

                    attackable = false;
                    Date newDate = new Date();

                    while (newDate.getTime() - attackDate.getTime() <= preDelay) {
                        newDate = new Date();
                    }

                    attackDate = new Date();
                    newDate = new Date();

                    while (newDate.getTime() - attackDate.getTime() <= animationTime) {
                        attackMethod();
                        newDate = new Date();
                    }

                    attackDate = new Date();
                    newDate = new Date();

                    while (newDate.getTime() - attackDate.getTime() <= postDelay) {
                        newDate = new Date();
                    }
                    attackable = true;

                }
            };
            attackThread.start();
        }
    }

    protected abstract Object attack_prepare();

    protected abstract void attackMethod();

}
