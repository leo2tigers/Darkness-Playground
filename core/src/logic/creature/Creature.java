package logic.creature;

import logic.GameMap;
import logic.GameObject;
import logic.Tile;
import logic.URect;

import java.util.Date;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Creature extends GameObject {

    public final String name;
    
    public int health;
    public int orientation = 1;
    public int maxHealth;
    public int armour = 0;
    public int attackPower;
    public double speedX;
    public double speedY = 0;
    public double jumping_speed = 50;
    public boolean jumping = false;
    public boolean movable = true;
    public boolean attackable = true;
    public int preDelay = 100;
    public int postDelay = 100;
    public URect hitBox, movementBox;
    public GameMap map;
    public Tile current_tile;
    public String status = "NORMAL";
    private Date attackDate;
    
    protected Texture img;

    public Creature(GameMap map, String name, int maxHealth, double positionX, double positionY, Texture img) {
    	this.map = map;
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.positionX = positionX;
        this.positionY = positionY;
        this.img = img;
    }

    public void setHitBox(double relativeX, double relativeY, double width, double height) {
        this.hitBox = new URect(positionX + relativeX, positionY + relativeY, width, height, Color.RED);
    }

    public void setMovementBox(double relativeX, double relativeY, double width, double height) {
        this.movementBox = new URect(positionX + relativeX, positionY + relativeY, width, height, Color.BLUE);
        this.current_tile = overlapTile(movementBox);
    }

    /**
     * This method is used to find the overlapped tile.
     * @param movementBox
     * @return Tile
     */
    private Tile overlapTile(URect movementBox) {
    	if (map != null) {
	        for (GameObject tile : map.getTiles()) {
	            if (tile instanceof Tile && movementBox.overlap((URect) tile)) {
	                return (Tile) tile;
	            }
	        }
    	}
        return null;
    }

    public void getHit(int damage) {
        setHealth(getHealth() - (damage - armour > 0 ? damage - armour : 0));
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

    public boolean isAlive() {
    	return health > 0;
    }

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
        
        URect check_movementBox = new URect(movementBox.positionX + x, movementBox.positionY + y, movementBox.width, movementBox.height, Color.BLUE);

        Tile check_tile = overlapTile(check_movementBox);
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
        if (movable) {
        	this.translate(this.speedX,  this.speedY);
        }
    }
    
    public void jump() {
        if (!jumping) {
        	jumping = true;
        	speedY = jumping_speed;
        }
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
            double gravity = 1000;
            speedY -= gravity;
        }
        move();
        this.speedX = 0;
    }

    /**
     * This method is used to begin an Attack Event if the creature is  alive and attackable.
     */
    public void attack() {
        if (isAlive()) {
        	attack_prepare();
            if (attackable) {
                attackDate = new Date();
                Thread attackThread = new Thread(() -> {
                	status = "ATTACKING";
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
                    status = "NORMAL";
                });
                attackThread.start();
            }
        }
    }

    protected abstract void attack_prepare();

    protected abstract void attackMethod();
    
    public void setImg(String img_path) {
		this.img = new Texture(img_path);
	}
    
    public abstract void render(SpriteBatch batch);

    public String getPosition() {
        return "( " + positionX + " , " + positionY + " )";
    }

    @Override
    public void shapeRender(ShapeRenderer shapeRenderer) {
    	this.hitBox.shapeRender(shapeRenderer);
    	this.movementBox.shapeRender(shapeRenderer);
    }
}
