package logic;

public class SpawnPoint {
    int positionX, positionY;
    MonsterType monsterType;

    public SpawnPoint(MonsterType monsterType, int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.monsterType = monsterType;
    }

    public Monster spawn() {
        return new Monster(monsterType.name, monsterType.maxHealth, positionX, positionY);
    }
}
