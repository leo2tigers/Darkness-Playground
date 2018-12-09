package logic.creature.monster;

import com.badlogic.gdx.graphics.Texture;

import logic.GameMap;
import logic.Meth;
import logic.Projectile;
import logic.SpawnPoint;

public class OwO_Ranger extends OwO {

	public OwO_Ranger(GameMap map, String name, double positionX, double positionY) {
		super(map, "Ranger-" + name, 2, positionX, positionY, new Texture("Monsters/Ranged OwO/new_ranged_owo.png"), new Texture("Monsters/Ranged OwO/new_ranged_owo_attack.png"), new Texture("Monsters/Ranged OwO/new_ranged_owo_jump.png"));
		this.max_sight_range = 750;
		this.attack_range = 400;
	}

	@Override
	protected void attack_prepare() {
		this.preDelay = 500;
		this.postDelay = 500;
	}
	
	@Override
	protected void attackMethod() {
		int damage = 1;
		map.add(new Projectile(positionX, positionY + this.hitBox.width/3, 
				               /*width*/20, /*height*/20, 
				               orientation, /*speed*/5, 
				               /*lifetime*/1000, damage, Projectile.TO_PLAYER, 
				               "Monsters/Ranged OwO/normal_projectile.png"));
	}
	
	public static Spawnable spawnable = new Spawnable() {
		@Override
		public Monster spawn(SpawnPoint spawnPoint) {
			return new OwO_Ranger(spawnPoint.map, "", Meth.center_random(spawnPoint.getX(), spawnPoint.spawnWidth), spawnPoint.getY());
		}
	};
}
