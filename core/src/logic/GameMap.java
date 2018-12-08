package logic;

import logic.creature.monster.Monster;
import logic.creature.player.Player;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameMap {

    private ArrayList<SpawnPoint> spawnPoints = new ArrayList<SpawnPoint>();
    private ArrayList<Tile> tiles;
    private ArrayList<Projectile> projectiles;
    private ArrayList<Monster> monsters;
    private ArrayList<Projectile> projectilesToRemove;
    private ArrayList<Monster> monstersToRemove;

    public Player player;
    public final ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
    public final ArrayList<GameObject> to_be_removed = new ArrayList<>();

    public GameMap() {
    	this.tiles = new ArrayList<Tile>();
    	this.projectiles = new ArrayList<Projectile>();
    	this.monsters = new ArrayList<Monster>();
    	this.projectilesToRemove = new ArrayList<Projectile>();
    	this.monstersToRemove = new ArrayList<Monster>();
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
		this.to_be_removed.add(gameObject);
	}

	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}
	
	public ArrayList<Monster> getMonsters()
	{
		return monsters;
	}
	
	public ArrayList<Projectile> getProjectilesToRemove() {
		return projectilesToRemove;
	}

	public ArrayList<Monster> getMonstersToRemove() {
		return monstersToRemove;
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
		/*for (GameObject gameObject : this.gameObjects) {
			gameObject.update();
		}*/
		for (Projectile projectile : this.projectiles)
		{
			projectile.update();
		}
		for (Monster monster : this.monsters)
		{
			monster.update();
		}
		for (GameObject gameObject : this.to_be_removed) {
			gameObjects.remove(gameObject);
		}
		this.player.update();
	}

}
