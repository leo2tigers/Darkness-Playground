package logic;

import logic.creature.Monster;
import logic.creature.Player;

import java.util.*;

public class GameMap {

    protected Player player;
    protected ArrayList<SpawnPoint> spawnPoints = new ArrayList<>();
    protected final ArrayList<Tile> tiles = new ArrayList<>();

    public final ArrayList<Monster> monsters = new ArrayList<>();

    public void setPlayer(Player player) {
        //TODO setPlayer()
        this.player = player;
        player.map = this;
    }

    public void addMonster(Monster monster) {
        //TODO addMonster()
        monsters.add(monster);
        monster.map = this;
    }

    public void addTile(Tile tile) {
        //TODO addTile()
        tiles.add(tile);
    }

    public void addSpawnPoint(SpawnPoint spawnPoint) {
        spawnPoints.add(spawnPoint);
    }

    public void spawnFromSpawnPoint(int spawnPointNumber) {
        SpawnPoint spawnPoint = spawnPoints.get(spawnPointNumber);
        Monster monster = spawnPoint.spawn();
        addMonster(monster);
    }
}
