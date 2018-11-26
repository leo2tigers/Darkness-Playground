package logic.creature;

import log.Log;
import logic.GameMap;
import logic.GameObject;
import logic.Tile;
import logic.URect;

import java.util.Date;

public abstract class Creature extends GameObject {

    private int health, maxHealth, armour = 0;
    protected int attackPower;
    protected int orientation = 1;

    public final String name ;
    protected double positionX, positionY;
    public double speedX;
    protected double speedY = 0;
    private double jumping_speed = 50;
    public URect hitBox, movementBox;
    public GameMap map;
    public Tile current_tile;
    public boolean jumping = false;
    protected boolean movable = true;
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

    public void setHitBox(double relativeX, double relativeY, double width, double height) {
        this.hitBox = new URect(positionX + relativeX, positionY + relativeY, width, height);
    }

    public void setMovementBox(double relativeX, double relativeY, double width, double height) {
        this.movementBox = new URect(positionX + relativeX, positionY + relativeY, width, height);
        this.current_tile = overlapTile(movementBox);
    }

    private Tile overlapTile(URect movementBox) {
        for (Tile tile : map.tiles) {
            if (movementBox.overlap(tile)) {
                return tile;
            }
        }
        return null;
    }

    public void getHit(int damage) {
        int prevHealth = getHealth();
        setHealth(getHealth() - (damage - armour > 0 ? damage - armour : 0));
    }

    private int getHealth() {
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

        URect check_movementBox = new URect(movementBox.positionX + x, movementBox.positionY + y, movementBox.width, movementBox.height);

        Tile check_tile = overlapTile(check_movementBox);
        System.out.println("\toverlap " + check_movementBox + "\n\t check ---> " + check_tile + "\n\t overlap ? " + check_movementBox.overlap(check_tile));
        if (check_movementBox.overlap(check_tile)) {
            new_positionY = check_tile.positionY + check_tile.height/2;
            jumping = false;
            speedY = 0;
        }else {
            jumping = true;
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

    public String getPosition() {
        return "( " + positionX + " , " + positionY + " )";
    }
}
