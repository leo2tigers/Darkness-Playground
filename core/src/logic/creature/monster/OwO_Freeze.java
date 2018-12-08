package logic.creature.monster;

import com.badlogic.gdx.graphics.Texture;

import logic.GameMap;
import logic.Meth;
import logic.SpawnPoint;
import logic.URect;

public class OwO_Freeze extends OwO {

	public OwO_Freeze(GameMap map, String name, double positionX, double positionY) {
		super(map, "Freeze-" + name, 2, positionX, positionY, new Texture("Monsters/Freeze OwO/new_owo_freeze.png"), new Texture("Monsters/Freeze OwO/new_owo_freeze_attack.png"), new Texture("Monsters/Freeze OwO/new_owo_freeze_jump.png"));
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
			return new OwO_Freeze(spawnPoint.map, "from_spawn_point_01", Meth.center_random(spawnPoint.positionX, spawnPoint.spawnWidth), spawnPoint.positionY);
		}
	};
}
