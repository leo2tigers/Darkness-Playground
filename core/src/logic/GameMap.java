package logic;

import logic.creature.Monster;
import logic.player.Player;

import java.util.*;

public class GameMap {

    private ArrayList<SpawnPoint> spawnPoints = new ArrayList<SpawnPoint>();

    private Player player;
    public final ArrayList<Tile> tiles = new ArrayList<Tile>();
    public final ArrayList<Monster> monsters = new ArrayList<Monster>();

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

    public void addAll(GameObject...args) {
        for (GameObject gameObject : args) {
            if (gameObject instanceof Monster) {
                addMonster((Monster) gameObject);
            }else if (gameObject instanceof SpawnPoint) {
                addSpawnPoint((SpawnPoint) gameObject);
            }else if (gameObject instanceof Tile) {
                addTile((Tile) gameObject);
            }
        }
    }
}
