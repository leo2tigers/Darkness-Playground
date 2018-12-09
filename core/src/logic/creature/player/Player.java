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
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.darknessplayground.game.screen.MainGame;

import logic.GameMap;
import logic.GameProperties;
import logic.creature.Creature;
import logic.exceptions.NoAmmoException;

public class Player extends Creature {
    public Gun gun;
    private float timeOutOfCombat;
    private float timeSinceLastRegen;
    private int xp;
    private int xpToCurrentLevel;
    private int xpToNextLevel;
    private int level;
    private MainGame screen;
    private float timeRunning;
    private Animation<Texture>[][] playerAnimation;
    private int animationState;
    private float stateTime;
    private float shootingAnimationDelay;

    public Player(GameMap map, String name, double positionX, double positionY, Gun gun, MainGame screen) {
        super(map, name, PlayerStats.MAX_HEALTH, positionX, positionY, new Texture("player_w-pistol_run2.png"));
        this.armour = PlayerStats.ARMOUR;
        this.speedX = 0;
        this.jumping_speed = PlayerStats.JUMPING_SPEED;
        this.setHitBox(PlayerStats.HitBox.RELATIVE_X, PlayerStats.HitBox.RELATIVE_Y, 
        		       PlayerStats.HitBox.WIDTH, PlayerStats.HitBox.HEIGHT);
        this.setMovementBox(PlayerStats.MovementBox.RELATIVE_X, PlayerStats.MovementBox.RELATIVE_Y, 
        		            PlayerStats.MovementBox.WIDTH, PlayerStats.MovementBox.HEIGHT);
        this.setGun(gun);
        this.screen = screen;
        this.timeOutOfCombat = 0;
        this.timeSinceLastRegen = 0;
        this.xp = 0;
        this.level = 1;
        this.xpToCurrentLevel = 0;
        this.xpToNextLevel = 200;
        
        this.playerAnimation = new Animation[2][7];
        this.playerAnimation[0][0] = new Animation<Texture>(0.2f, new Texture("Player/Pistol/player_w-pistol_stand_still_left.png"));
        this.playerAnimation[0][1] = new Animation<Texture>(0.2f, new Texture("Player/Pistol/player_w-pistol_run1_left.png"));
        this.playerAnimation[0][2] = new Animation<Texture>(0.2f, new Texture("Player/Pistol/player_w-pistol_run2_left.png"));
        this.playerAnimation[0][3] = new Animation<Texture>(0.2f, new Texture("Player/Pistol/player_w-pistol_run3_left.png"));
        this.playerAnimation[0][4] = new Animation<Texture>(0.2f, new Texture("Player/Pistol/player_w-pistol_run4_left.png"));
        this.playerAnimation[0][5] = new Animation<Texture>(0.3f, new Texture("Player/Pistol/player_w-pistol_fire_left.png"));
        this.playerAnimation[0][6] = new Animation<Texture>(0.3f, new Texture("Player/Pistol/player_w-pistol_after_fire_left.png"));
        this.playerAnimation[1][0] = new Animation<Texture>(0.2f, new Texture("Player/Pistol/player_w-pistol_stand_still_right.png"));
        this.playerAnimation[1][1] = new Animation<Texture>(0.2f, new Texture("Player/Pistol/player_w-pistol_run1_right.png"));
        this.playerAnimation[1][2] = new Animation<Texture>(0.2f, new Texture("Player/Pistol/player_w-pistol_run2_right.png"));
        this.playerAnimation[1][3] = new Animation<Texture>(0.2f, new Texture("Player/Pistol/player_w-pistol_run3_right.png"));
        this.playerAnimation[1][4] = new Animation<Texture>(0.2f, new Texture("Player/Pistol/player_w-pistol_run4_right.png"));
        this.playerAnimation[1][5] = new Animation<Texture>(0.3f, new Texture("Player/Pistol/player_w-pistol_fire_right.png"));
        this.playerAnimation[1][6] = new Animation<Texture>(0.3f, new Texture("Player/Pistol/player_w-pistol_after_fire_right.png"));
        this.timeRunning = 0;
        this.animationState = 0;
        this.stateTime = 0;
        this.shootingAnimationDelay = 0;
    }

	private void setGun(Gun gun) {
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

	private int calculateXpToNextLevel(int lastLevelXp)
	{
		int multiplier = (this.level - (this.level % 10)) / 10;
		return lastLevelXp + 200 + (100*multiplier);
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

	
	public float getTimeRunning() {
		return timeRunning;
	}

	public void setTimeRunning(float timeRunning) {
		this.timeRunning = timeRunning;
	}

	public int getAnimationState() {
		return animationState;
	}

	public void setAnimationState(int animationState) {
		if(animationState < 0) this.animationState = 0;
		else if(animationState > 6) this.animationState = 6;
		else this.animationState = animationState;
		
		// Dynamic HitBox/MovementBox
		if (this.animationState == 0) {
			this.setHitBox(PlayerStats.HitBox.RELATIVE_X, PlayerStats.HitBox.RELATIVE_Y, 
     		               PlayerStats.HitBox.WIDTH/2, PlayerStats.HitBox.HEIGHT);
			this.setMovementBox(PlayerStats.MovementBox.RELATIVE_X, PlayerStats.MovementBox.RELATIVE_Y, 
     		                    PlayerStats.MovementBox.WIDTH/2, PlayerStats.MovementBox.HEIGHT);
		} else {
			this.setHitBox(PlayerStats.HitBox.RELATIVE_X, PlayerStats.HitBox.RELATIVE_Y, 
     		               PlayerStats.HitBox.WIDTH, PlayerStats.HitBox.HEIGHT);
			this.setMovementBox(PlayerStats.MovementBox.RELATIVE_X, PlayerStats.MovementBox.RELATIVE_Y, 
     		                    PlayerStats.MovementBox.WIDTH, PlayerStats.MovementBox.HEIGHT);
		}
	}
	
	public void calculateAnimationState()
	{
		this.setAnimationState((int)(this.timeRunning*(10))%4 + 1);
	}

	public float getShootingAnimationDelay() {
		return shootingAnimationDelay;
	}

	public void setShootingAnimationDelay(float shootingAnimationDelay) {
		this.shootingAnimationDelay = shootingAnimationDelay;
	}

    @Override
    public void update() {
        if (jumping) {
            speedY -= GameProperties.Constant.GRAVITY;
        }
        move();
        this.speedX = 0;
        this.regenHP(Gdx.graphics.getDeltaTime());
    }

    @Override
    protected void attack_prepare() {}

    @Override
    protected void attackMethod() {
    	try {
    		gun.fire_method();
    	}
    	catch(NoAmmoException e)
    	{
    		this.screen.showNotice("No Ammo");
    	}
    }
    
    @Override
    public void getHit(int damage) {
        setHealth(getHealth() - (damage - armour > 0 ? damage - armour : 0));
        this.inCombat();
    }

	@Override
	public String toString() {
		return super.toString() + " , " + (gun != null ? gun : "unarmed") + " , XP = " + this.xp + " / " + this.xpToNextLevel;
	}
	
	@Override
	public void render(SpriteBatch batch)
	{
		int orientation = -1;
		if(this.orientation == 1)
		{
			orientation = 1;
		}
		else if(this.orientation == -1)
		{
			orientation = 0;
		}
		batch.draw(this.playerAnimation[orientation][this.animationState].getKeyFrame(stateTime, true), (float)positionX, (float)positionY);
	}

	@Override
	public void dispose()
	{
		this.img.dispose();
		this.gun.dispose();
	}
}
