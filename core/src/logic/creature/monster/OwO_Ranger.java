package logic.creature.monster;

import com.badlogic.gdx.graphics.Texture;

import logic.GameMap;
import logic.Meth;
import logic.Projectile;
import logic.SpawnPoint;

public class OwO_Ranger extends OwO {

	public OwO_Ranger(GameMap map, String name, double positionX, double positionY) {
		super(map, "Ranger-" + name, 2, positionX, positionY,
			  new Texture("Monsters/Ranged OwO/new_ranged_owo.png"),
			  new Texture("Monsters/Ranged OwO/new_ranged_owo_attack_left.png"),
			  new Texture("Monsters/Ranged OwO/new_ranged_owo_attack_right.png"),
			  new Texture("Monsters/Ranged OwO/new_ranged_owo_jump.png"));
		this.max_sight_range = 750;
	}

	@Override
	protected void attack_prepare() {
	}
	
	@Override
	protected void attackMethod() {
		int damage = 1;
		map.add(new Projectile(positionX, positionY, 
				               /*width*/10, /*height*/10, 
				               orientation, /*speed*/25, 
				               /*lifetime*/50, damage, Projectile.TO_PLAYER, 
				               "Monsters/Ranged OwO/normal_projectile.png"));
	}
	
	public static Spawnable spawnable = new Spawnable() {
		@Override
		public Monster spawn(SpawnPoint spawnPoint) {
			return new OwO_Ranger(spawnPoint.map, "from_spawn_point_01", Meth.center_random(spawnPoint.getX(), spawnPoint.spawnWidth), spawnPoint.getY());
		}
	};
}
