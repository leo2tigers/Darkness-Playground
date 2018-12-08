package logic.creature.monster;

import logic.SpawnPoint;

public interface Spawnable {
	abstract Monster spawn(SpawnPoint spawnPoint);
}
