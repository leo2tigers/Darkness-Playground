package logic;

import java.util.*;

public class GameMap {
    Player player;
    final ArrayList<Monster> monsters = new ArrayList<>();
    final ArrayList<SpawnPoint> spawnPoints = new ArrayList<>();
    final ArrayList<Tile> tiles = new ArrayList<>();

    public void setPlayer(Player player) {
        this.player = player;
        player.map = this;
    }

    public void addMonster(Monster monster) {
        monsters.add(monster);
    }

    public void addTile(Tile tile) {
        tiles.add(tile);
    }

    public void addSpawnPoint(SpawnPoint spawnPoint) {
        spawnPoints.add(spawnPoint);
    }

    public void spawnFromSpawnPoint(int spawnPointNumber) {
        monsters.add(spawnPoints.get(spawnPointNumber).spawn());
    }
}
