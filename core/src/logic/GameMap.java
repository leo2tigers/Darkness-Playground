package logic;

import logic.creature.Monster;
import logic.player.Player;

import java.util.*;

public class GameMap {

    private ArrayList<SpawnPoint> spawnPoints = new ArrayList<SpawnPoint>();

    private Player player;
    public final ArrayList<GameObject> gameObjects = new ArrayList<>();

    public GameMap() {}

    public void setPlayer(Player player) {
        this.player = player;
        player.map = this;
    }

    public void add(GameObject gameObject) {
        gameObjects.add(gameObject);
        gameObject.map = this;
    }

    public void addAll(GameObject...args) {
        for (GameObject gameObject : args) {
            gameObjects.add(gameObject);
            gameObject.map = this;
        }
    }
    
    public void addSpawnPoint(SpawnPoint spawnPoint) {
        spawnPoints.add(spawnPoint);
    }

    public void spawnFromSpawnPoint(int spawnPointNumber) {
        SpawnPoint spawnPoint = spawnPoints.get(spawnPointNumber);
        Monster monster = spawnPoint.spawn();
        add(monster);
    }
}
