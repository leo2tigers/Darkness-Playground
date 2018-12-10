package logic.creature.monster;

import java.util.Date;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import logic.GameMap;
import logic.Meth;
import logic.Projectile;
import logic.SpawnPoint;
import logic.creature.player.Player;
import logic.utilities.Randomizer;

public class OwO_Sniper extends OwO {
	
	private Sound fireSound;

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
		this.fireSound = Gdx.audio.newSound(Gdx.files.internal("Sfx/Sniper_OwO_Attack.mp3"));
	}

	@Override
	protected void attack_prepare() {
    	this.preDelay = 2500;
    	this.postDelay = 2000;
	}
	
	@Override
	protected void attackMethod() {
		int damage = Randomizer.getDamageValue(20, 45);
		if(this.orientation == -1) {
			map.add(new Projectile(positionX, positionY + this.hitBox.height/3,
				               	   /*width*/75, /*height*/25, 
				               	   orientation, /*speed*/5, 
				               	   /*lifetime*/5000, damage, Projectile.TO_PLAYER, 
				               	   "Monsters/Sniper OwO/sniper_projectile_left.png",
				               	   "Monsters/Sniper OwO/sniper_projectile_hit_left.png"));
		}
		else if(this.orientation == 1) {
			map.add(new Projectile(positionX + this.img.getWidth(), positionY + this.hitBox.height/3,
	               	   			   /*width*/75, /*height*/25, 
	               	   			   orientation, /*speed*/5, 
	               	   			   /*lifetime*/5000, damage, Projectile.TO_PLAYER, 
	               	   			   "Monsters/Sniper OwO/sniper_projectile_right.png",
	               	   			   "Monsters/Sniper OwO/sniper_projectile_hit_right.png"));
		}
	}
	
	@Override
	public void attack() {
        if (isAlive()) {
        	attack_prepare();
            if (attackable) {
                attackDate = new Date();
                Thread attackThread = new Thread(() -> {
                	status = "ATTACKING";
                    // preAnimation delay
                    attackable = false;
                    this.setAttackState(1);
                    Date newDate = new Date();
                    while (newDate.getTime() - attackDate.getTime() <= preDelay) {
                        newDate = new Date();
                    }

                    // attack!
                    if (isAlive()) {
                    	this.fireSound.play();
                    	attackMethod();
                    } else {
                    	return;
                    }
                    this.setAttackState(0);

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
	
	@Override
	public void grantXp(Player player)
	{
		player.addXp(80);
	}
	
	public static Spawnable spawnable = new Spawnable() {
		@Override
		public Monster spawn(SpawnPoint spawnPoint) {
			return new OwO_Sniper(spawnPoint.map, "from_spawn_point", Meth.center_random(spawnPoint.getX(), spawnPoint.spawnWidth), spawnPoint.getY());
		}
	};
}
