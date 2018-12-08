/**
 * The Player class represents a player character controlled by a player.
 * 
 * @author 13thFloorGuy
 * @version 0.0
 * @since 12/04/2018
 */
package logic.creature.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import logic.GameMap;
import logic.creature.Creature;

public class Player extends Creature {
    public Gun gun;
    private float timeOutOfCombat;
    private float timeSinceLastRegen;
    private int xp;
    private int xpToCurrentLevel;
    private int xpToNextLevel;
    private int level;

    public Player(GameMap map, String name, double positionX, double positionY, Gun gun) {
        super(map, name, PlayerStats.MAX_HEALTH, positionX, positionY, new Texture("player_w-pistol_run2.png"));
        this.armour = PlayerStats.ARMOUR;
        this.speedX = 0;
        this.jumping_speed = PlayerStats.JUMPING_SPEED;
        this.setHitBox(PlayerStats.HitBox.RELATIVE_X, PlayerStats.HitBox.RELATIVE_Y, 
        		       PlayerStats.HitBox.WIDTH, PlayerStats.HitBox.HEIGHT);
        this.setMovementBox(PlayerStats.MovementBox.RELATIVE_X, PlayerStats.MovementBox.RELATIVE_Y, 
        		            PlayerStats.MovementBox.WIDTH, PlayerStats.MovementBox.HEIGHT);
        this.setGun(gun);
        this.timeOutOfCombat = 0;
        this.timeSinceLastRegen = 0;
        this.xp = 0;
        this.level = 1;
        this.xpToCurrentLevel = 0;
        this.xpToNextLevel = 200;
    }
    
    @Override
    public void update() {
        if (jumping) {
            double gravity = 1;
            speedY -= gravity;
        }
        move();
        this.speedX = 0;
        this.regenHP(Gdx.graphics.getDeltaTime());
    }

    @Override
    protected void attack_prepare() {/*
        if (attackable && gun.ammo != 0) {
        } else if (!gun.reloading && gun.ammo == 0) {
            attackable = false;
            gun.reload();
        } else {
        	gun.reload_interrupt();
        }*/
    }

    @Override
    protected void attackMethod() {
        gun.fire_method();
    }
    
    @Override
    public void getHit(int damage) {
        setHealth(getHealth() - (damage - armour > 0 ? damage - armour : 0));
        this.inCombat();
    }

	public void setGun(Gun gun) {
		this.gun = gun;
		if (this.gun != null) {
			this.gun.owner = this;
			this.preDelay = this.gun.preDelay;
			this.postDelay = this.gun.postDelay;
		} else {
			this.preDelay = 0;
			this.postDelay = 0;
			this.attackable = false;
		}
	}
	
	@Override
	public String toString() {
		return super.toString() + " , " + (gun != null ? gun : "unarmed");
	}
	
	@Override
	public void render(SpriteBatch batch)
	{
		batch.draw(this.img, (float)positionX, (float)positionY);
	}

	public void moveLeft() {
		this.speedX = -PlayerStats.MOVEMENT_SPEED;
		this.orientation = -1;
	}

	public void moveRight() {
		this.speedX = PlayerStats.MOVEMENT_SPEED;
		this.orientation = 1;
	}
	
	public void regenHP(float dt)
	{
		this.timeOutOfCombat += dt;
		if(this.timeOutOfCombat >= 5)
		{
			this.timeSinceLastRegen += dt;
			if(this.timeSinceLastRegen >= 1)
			{
				this.timeSinceLastRegen -= 1;
				if(this.timeOutOfCombat < 10)
				{
					this.setHealth(this.getHealth() + 1);
				}
				else
				{
					this.setHealth(this.getHealth() + 2);
				}
			}
		}
	}
	
	public void xpFromTime(int xp)
	{
		this.addXp(xp);
	}
	
	public void inCombat()
	{
		this.timeOutOfCombat = 0;
		this.timeSinceLastRegen = 0;
	}
	
	public void addXp(int xp)
	{
		this.xp += xp;
		if(this.xp >= this.xpToNextLevel)
		{
			this.level++;
			this.xpToCurrentLevel = this.xpToNextLevel;
			this.xpToNextLevel = this.calculateXpToNextLevel(this.xpToCurrentLevel);
		}
	}
	
	private int calculateXpToNextLevel(int lastLevelXp)
	{
		int multiplier = (this.level - (this.level % 10)) / 10;
		return lastLevelXp + 200 + (100*multiplier);
	}
	
	@Override
	public void dispose()
	{
		this.img.dispose();
		this.gun.dispose();
	}
}
