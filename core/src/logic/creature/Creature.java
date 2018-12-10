package logic.creature;

import logic.GameMap;
import logic.GameObject;
import logic.GameProperties;
import logic.Tile;
import logic.URect;

import java.util.Date;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.darknessplayground.game.screen.MainGame;

public abstract class Creature extends GameObject {

    public final String name;
    
    protected int health;
    protected int orientation = 1;
    protected int maxHealth;
    protected int armour = 0;
    protected int attackPower;
    protected double movementSpeed;
    protected double speedX;
    protected double speedY = 0;
    protected double jumpingSpeed = 50;
    protected boolean jumping = false;
    protected boolean movable = true;
    protected boolean attackable = true;
    protected int preDelay = 100;
    protected int postDelay = 100;
    protected URect hitBox;
    protected URect movementBox;
    protected Tile currentTile;
    protected String status = "NORMAL";
    protected Date attackDate;
	private boolean alive;
    
    protected Texture img;


    public Creature(GameMap map, String name, int maxHealth, double positionX, double positionY, Texture img) {
    	super(positionX, positionY, map);
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.img = img;
        this.alive();
    }

    public void setHitBox(double relativeX, double relativeY, double width, double height) {
        this.hitBox = new URect(positionX + relativeX, positionY + relativeY, width, height, Color.RED);
    }

    public void setMovementBox(double relativeX, double relativeY, double width, double height) {
        this.movementBox = new URect(positionX + relativeX, positionY + relativeY, width, height, Color.BLUE);
        this.currentTile = overlapTile(movementBox);
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
    	return this.alive;
    }
    
    public void alive() {
    	this.alive = true;
    }
    public void die() {
    	this.alive = false;
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
        
        URect check_movementBox = new URect(movementBox.getX() + x, movementBox.getY() + y, movementBox.getWidth(), movementBox.getHeight(), Color.BLUE);

        Tile check_tile = overlapTile(check_movementBox);
        if (check_movementBox.overlap(check_tile) && Math.abs(check_tile.getX() +check_tile.getWidth()/2 - this.positionX - this.movementBox.getWidth()/2) <= check_tile.getWidth()/2) {
            new_positionY = check_tile.getY() + check_tile.getHeight();
            jumping = false;
            speedY = 0;
        }else {
            jumping = true;
        }
        
        if (new_positionX < 0) {
        	new_positionX = 0;
        } else  if (new_positionX > 1280 - this.movementBox.getWidth()) {
        	new_positionX = 1280 - this.movementBox.getWidth();
        }
		
        movementBox.translate(new_positionX - positionX, new_positionY - positionY);
        hitBox.translate(new_positionX - positionX, new_positionY - positionY);

        positionX = new_positionX;
        positionY = new_positionY;
        
        this.currentTile = this.overlapTile(this.movementBox);

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
        	speedY = jumpingSpeed;
        }
    }
    public void jump_down() {
        jumping = true;
        speedY = -jumpingSpeed*1.3;
        translate(0., speedY);
        speedY = 0;
    }

    @Override
    public String toString() {
        return name + " ( " + (isAlive() ? "ALIVE" : "DEAD") +  " ) , health = " + health + "/" + maxHealth;
    }

    public void update() {
        if (jumping) {
            speedY -= GameProperties.Constant.GRAVITY;
        }
        move();
        this.speedX = 0;
    }

    /**
     * This method is used to begin an Attack Event if the creature is  alive and attackable.
     */
    public void attack() {
    	MainGame.log(this.name + " attack ");
        if (isAlive()) {
        	attack_prepare();
            if (attackable) {
                attackDate = new Date();
                Thread attackThread = new Thread(new Runnable() {
                	@Override
                	public void run() {
	                	status = "ATTACKING";
	                    // preAnimation delay
	                    attackable = false;
	                    Date newDate = new Date();
	                    while (newDate.getTime() - attackDate.getTime() <= preDelay) {
	                        newDate = new Date();
	                    }
	
	                    // attack!
	                    if (isAlive()) {
	                    	attackMethod();
	                    } else {
	                    	return;
	                    }
	
	                    // postAnimation delay
	                    attackDate = new Date();
	                    newDate = new Date();
	                    while (newDate.getTime() - attackDate.getTime() <= postDelay) {
	                        newDate = new Date();
	                    }
	                    attackable = true;
	                    status = "NORMAL";
                	}
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
    
    public void dispose()
    {
    	this.img.dispose();
    }

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public int getArmour() {
		return armour;
	}

	public void setArmour(int armour) {
		this.armour = armour;
	}

	public int getAttackPower() {
		return attackPower;
	}

	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}

	public double getMovementSpeed() {
		return movementSpeed;
	}

	public void setMovementSpeed(double movementSpeed) {
		this.movementSpeed = movementSpeed;
	}

	public double getSpeedX() {
		return speedX;
	}

	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}

	public double getSpeedY() {
		return speedY;
	}

	public void setSpeedY(double speedY) {
		this.speedY = speedY;
	}

	public double getJumpingSpeed() {
		return jumpingSpeed;
	}

	public void setJumpingSpeed(double jumpingSpeed) {
		this.jumpingSpeed = jumpingSpeed;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public boolean isMovable() {
		return movable;
	}

	public void setMovable(boolean movable) {
		this.movable = movable;
	}

	public boolean isAttackable() {
		return attackable;
	}

	public void setAttackable(boolean attackable) {
		this.attackable = attackable;
	}

	public int getPreDelay() {
		return preDelay;
	}

	public void setPreDelay(int preDelay) {
		this.preDelay = preDelay;
	}

	public int getPostDelay() {
		return postDelay;
	}

	public void setPostDelay(int postDelay) {
		this.postDelay = postDelay;
	}

	public URect getHitBox() {
		return hitBox;
	}

	public void setHitBox(URect hitBox) {
		this.hitBox = hitBox;
	}

	public URect getMovementBox() {
		return movementBox;
	}

	public void setMovementBox(URect movementBox) {
		this.movementBox = movementBox;
	}

	public Tile getCurrentTile() {
		return currentTile;
	}

	public void setCurrentTile(Tile currentTile) {
		this.currentTile = currentTile;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getAttackDate() {
		return attackDate;
	}

	public void setAttackDate(Date attackDate) {
		this.attackDate = attackDate;
	}

	public String getName() {
		return name;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}
