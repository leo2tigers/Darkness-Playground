package logic;

import logic.creature.Monster;
import logic.player.Player;

import java.util.*;
import java.util.function.Function;

public class GameMap {

    private ArrayList<SpawnPoint> spawnPoints = new ArrayList<SpawnPoint>();

    private Player player;
    public final ArrayList<GameObject> gameObjects = new ArrayList<>();

    public GameMap() {}

    public void setPlayer(Player player) {
        //TODO setPlayer()
        this.player = player;
        player.map = this;
    }

    public void add(GameObject gameObject) {
        gameObjects.add(gameObject);
        gameObject.map = this;
    }

    public void addSpawnPoint(SpawnPoint spawnPoint) {
        spawnPoints.add(spawnPoint);
    }

    public void spawnFromSpawnPoint(int spawnPointNumber) {
        SpawnPoint spawnPoint = spawnPoints.get(spawnPointNumber);
        Monster monster = spawnPoint.spawn();
        add(monster);
    }

    public void addAll(GameObject...args) {
        for (GameObject gameObject : args) {
            gameObjects.add(gameObject);
            gameObject.map = this;
        }
    }

    public interface game_function<Type extends GameObject, Return> {
        Return apply(Type gameObject);
    }

    public <Type extends  GameObject,Return> Return for_all(game_function<Type,Return> function) {
        Return r = null;
        for (GameObject gameObject : gameObjects) {
            if (gameObject != null) {
                r = function.apply((Type) gameObject);
            }
        }
        return r;
    }
}
