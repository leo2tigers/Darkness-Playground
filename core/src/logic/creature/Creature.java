package logic;

import logic.GameMap;
import logic.GameObject;
import logic.Tile;
import logic.URect;

import java.util.Date;

public abstract class Creature extends GameObject {

    public int health;
    private int maxHealth;
    private int armour = 0;
    protected int attackPower;
    public int orientation = 1;

    public final String name ;
    public double speedX;
    protected double speedY = 0;
    private double jumping_speed = 50;
    public URect hitBox, movementBox;
    public GameMap map;
    public Tile current_tile;
    public boolean jumping = false;
    protected boolean movable = true;
    public boolean attackable = true;
    private int preDelay = 100, postDelay = 100;
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

    /**
     * This method is used to find the overlapped tile.
     * @param movementBox
     * @return Tile
     */
    private Tile overlapTile(URect movementBox) {
        for (GameObject tile : map.gameObjects) {
            if (tile instanceof Tile && movementBox.overlap((URect) tile)) {
                return (Tile) tile;
            }
        }
        return null;
    }

    public void getHit(int damage) {
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

    public boolean isAlive() {return health > 0;}

    /**
     * This method is used to translate the creature and its components
     * by create new movementBox and check whether it overlap any tile.
     * If there is any overlapped tile, the new position will be on that tile.
     * @param x
     * @param y
     * @return
     */
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

    /**
     * This method is used to begin an Attack Event if the creature is  alive and attackable.
     */
    public void attack() {
<<<<<<< HEAD:core/src/logic/Creature.java
        if (attackable && isAlive()) {
        	
        	attack_prepare();

=======
        if (isAlive()) {
        	attack_prepare();
>>>>>>> master:core/src/logic/creature/Creature.java
            if (attackable) {
                attackDate = new Date();
                Thread attackThread = new Thread(() -> {

                    // preAnimation delay
                    attackable = false;
                    Date newDate = new Date();
                    while (newDate.getTime() - attackDate.getTime() <= preDelay) {
                        newDate = new Date();
                    }

                    // attack!
                    attackMethod();

                    // postAnimation delay
                    attackDate = new Date();
                    newDate = new Date();
                    while (newDate.getTime() - attackDate.getTime() <= postDelay) {
                        newDate = new Date();
                    }
                    attackable = true;
                });
                attackThread.start();
            }
        }
    }

<<<<<<< HEAD:core/src/logic/Creature.java
    protected abstract String attack_prepare();
=======
    protected abstract void attack_prepare();

>>>>>>> master:core/src/logic/creature/Creature.java
    protected abstract void attackMethod();

    public String getPosition() {
        return "( " + positionX + " , " + positionY + " )";
    }
}
