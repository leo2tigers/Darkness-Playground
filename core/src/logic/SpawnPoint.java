package logic;

import logic.creature.Monster;
import logic.creature.MonsterType;

public class SpawnPoint {

    int positionX, positionY;
    int spawnCount = 0;
    MonsterType monsterType;

    public SpawnPoint(MonsterType monsterType, int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.monsterType = monsterType;
    }

    public Monster spawn() {
        Monster monster = new Monster(
                monsterType.TypeName + "-" + spawnCount++,
                monsterType.maxHealth,
                positionX,
                positionY
        );
        monster.movementBox.translate(positionX, positionY);
        return monster;
    }

}
