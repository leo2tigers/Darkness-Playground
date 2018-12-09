package logic.creature.monster;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import logic.GameMap;
import logic.Meth;
import logic.Projectile;
import logic.SpawnPoint;

public class OwO_Sniper extends OwO {

	public OwO_Sniper(GameMap map, String name, double positionX, double positionY) {
		super(map, "Sniper-" + name, 5, 
		      positionX, positionY, 
		      new Texture("Monsters/Sniper OwO/new_sniper_owo.png"), 
		      new Texture("Monsters/Sniper OwO/new_sniper_owo_attack_left.png"),
		      new Texture("Monsters/Sniper OwO/new_sniper_owo_attack_right.png"), 
		      new Texture("Monsters/Sniper OwO/new_sniper_owo_jump.png"));
		this.max_sight_range = 3000;
		this.attack_range = 2000;
		this.movement_speed = 0.5;
		this.attackState = 0;
	}

	@Override
	protected void attack_prepare() {
    	this.preDelay = 2500;
    	this.postDelay = 2000;
	}
	
	@Override
	protected void attackMethod() {
		int damage = 10;
		map.add(new Projectile(positionX, positionY + this.hitBox.height/3,
				               /*width*/75, /*height*/25, 
				               orientation, /*speed*/10, 
				               /*lifetime*/5000, damage, Projectile.TO_PLAYER, 
				               "Monsters/Sniper OwO/sniper_projectile.png",
				               "Monsters/Sniper OwO/sniper_projectile_hit.png"));
	}
	
	@Override
	public void render(SpriteBatch batch)
	{
		if(this.attackable) {
			this.attackState = 0;
		}
		if(this.jumping) {
			batch.draw(jumpImg, (float) getX(), (float)getY());
		}
		if(this.attackState == 0) {
			batch.draw(this.img, (float) getX(), (float) getY());
		}
		else if(this.attackState == 1) {
			if(this.orientation == -1) {
				batch.draw(this.atkImg[0], (float) getX(), (float) getY());
			}
			else if(this.orientation == 1) {
				batch.draw(this.atkImg[1], (float) getX(), (float) getY());
			}
		}
	}
	
	public static Spawnable spawnable = new Spawnable() {
		@Override
		public Monster spawn(SpawnPoint spawnPoint) {
			return new OwO_Sniper(spawnPoint.map, "", Meth.center_random(spawnPoint.getX(), spawnPoint.spawnWidth), spawnPoint.getY());
		}
	};
}
