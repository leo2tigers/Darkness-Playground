/**
 * The Player class represents a player character controlled by a player.
 * 
 * @author 13thFloorGuy
 * @version 0.0
 * @since 12/04/2018
 */
package logic.creature.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import logic.creature.Creature;

public class Player extends Creature {
    public Gun gun;



    public Player(String name, double positionX, double positionY, Gun gun) {
        super(name, PlayerStats.MAX_HEALTH, positionX, positionY, new Texture("player_w-pistol_run2.png"));
        this.armour = PlayerStats.ARMOUR;
        this.speedX = 0;
        this.jumping_speed = PlayerStats.JUMPING_SPEED;
        this.setHitBox(PlayerStats.HitBox.RELATIVE_X, PlayerStats.HitBox.RELATIVE_Y, 
        		       PlayerStats.HitBox.WIDTH, PlayerStats.HitBox.HEIGHT);
        this.setMovementBox(PlayerStats.MovementBox.RELATIVE_X, PlayerStats.MovementBox.RELATIVE_Y, 
        		            PlayerStats.MovementBox.WIDTH, PlayerStats.MovementBox.HEIGHT);
        this.setGun(gun);
    }

    @Override
    protected void attack_prepare() {
        if (attackable && gun.ammo != 0) {
        } else if (!gun.reloading) {
            attackable = false;
            gun.reload();
        } else {
        	gun.reload_interrupt();
        }
    }

    @Override
    protected void attackMethod() {
        gun.fire();
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

	public void moveRigth() {
		this.speedX = PlayerStats.MOVEMENT_SPEED;
		this.orientation = 1;
	}
}
