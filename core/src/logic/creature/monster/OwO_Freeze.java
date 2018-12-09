package logic.creature.monster;

import com.badlogic.gdx.graphics.Texture;

import logic.GameMap;
import logic.Meth;
import logic.SpawnPoint;
import logic.URect;

public class OwO_Freeze extends OwO {

	public OwO_Freeze(GameMap map, String name, double positionX, double positionY) {
		super(map, "Freeze-" + name, 2, positionX, positionY,
			  new Texture("Monsters/Freeze OwO/new_owo_freeze.png"),
			  new Texture("Monsters/Freeze OwO/new_owo_freeze_attack_left.png"),
			  new Texture("Monsters/Freeze OwO/new_owo_freeze_attack_right.png"),
			  new Texture("Monsters/Freeze OwO/new_owo_freeze_jump.png"));
		this.max_sight_range = 750;
	}
	
	@Override
	protected void attack_prepare() {
	}
	
	@Override
	protected void attackMethod() {
		URect damageBox = new URect(positionX, positionY, 200, 50);
    	if (damageBox.overlap(map.player.hitBox)) {
    		map.player.getHit(1);
    	}
	}

	public static Spawnable spawnable = new Spawnable() {
		@Override
		public Monster spawn(SpawnPoint spawnPoint) {
			return new OwO_Freeze(spawnPoint.map, "from_spawn_point_01", Meth.center_random(spawnPoint.getX(), spawnPoint.spawnWidth), spawnPoint.getY());
		}
	};
}
