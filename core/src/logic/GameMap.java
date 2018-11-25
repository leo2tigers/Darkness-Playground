package logic;

import logic.creature.Monster;
import logic.player.Player;

import java.util.*;

public class GameMap {

    private ArrayList<SpawnPoint> spawnPoints = new ArrayList<>();

    private Player player;
    public final ArrayList<Tile> tiles = new ArrayList<>();
    public final ArrayList<Monster> monsters = new ArrayList<>();

    public GameMap() {}

    public void setPlayer(Player player) {
        //TODO setPlayer()
        this.player = player;
        player.map = this;
    }

    private void addMonster(Monster monster) {
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
