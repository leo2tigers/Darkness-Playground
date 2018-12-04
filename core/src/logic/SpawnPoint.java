package logic;

import logic.monster.Monster;
import logic.monster.MonsterType;

public class SpawnPoint extends GameObject {
    private int spawnCount = 0;
    private MonsterType monsterType;

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

    @Override
    public void update() {
        return;
    }
}
