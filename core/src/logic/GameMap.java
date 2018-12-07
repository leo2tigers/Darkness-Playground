package logic;

import logic.creature.monster.Monster;
import logic.creature.player.Player;

import java.util.*;
import java.util.function.Function;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameMap {

    private ArrayList<SpawnPoint> spawnPoints = new ArrayList<SpawnPoint>();
    public ArrayList<Tile> tiles;
    private ArrayList<Projectile> projectiles;

    public Player player;
    public final ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

    public GameMap() {
    	this.tiles = new ArrayList<Tile>();
    	this.projectiles = new ArrayList<Projectile>();
    	this.tiles.add(new Tile(0, 0, 1200, 100, new Texture("Tiles/playground_floor.png")));
    }

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

	public void remove(GameObject gameObject) {
		gameObjects.remove(gameObject);
	}

	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}
	
	public void render(SpriteBatch batch)
	{
		for (Tile t : this.tiles) {
			t.render(batch);
		}
		this.player.render(batch);
	}

	public void render(ShapeRenderer shapeRenderer) {
		for (GameObject gameObject : this.gameObjects) {
			gameObject.shapeRender(shapeRenderer);
		}
		player.shapeRender(shapeRenderer);
	}

	public void updateAll() {
		for (GameObject gameObject : this.gameObjects) {
			gameObject.update();
		}
		this.player.update();
	}

}
