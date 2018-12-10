package logic;

import logic.creature.monster.Monster;
import logic.creature.player.Player;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.darknessplayground.game.screen.MainGame;

public class GameMap {

    private final ArrayList<SpawnPoint> spawnPoints;
    private final ArrayList<Tile>       tiles;
    private ArrayList<Projectile> projectiles;
    private ArrayList<Monster>    monsters;
    private ArrayList<GameObject>  toBeRemoved;

    public Player player;

    public GameMap() {
    	spawnPoints = new ArrayList<SpawnPoint>();
    	tiles = new ArrayList<Tile>();
    	projectiles = new ArrayList<Projectile>();
    	monsters = new ArrayList<Monster>();
    	toBeRemoved = new ArrayList<>();
    }

    public void setPlayer(Player player) {
        this.player = player;
        player.map = this;
        
    }

	public void add(Tile tile) {
		this.tiles.add(tile);
	}
	
	public void add(Monster monster) {
		monsters.add(monster);
		monster.map = this;
	}
    
    public void add(Projectile projectile) {
    	if (projectile != null) {
	        projectiles.add(projectile);
	        projectile.map = this;
    	} else {
    		throw new NullPointerException();
    	}
    }
    
    public void addSpawnPoint(SpawnPoint spawnPoint) {
        spawnPoints.add(spawnPoint);
        spawnPoint.map = this;
    }

    public void spawnFromSpawnPoint(int spawnPointNumber) {
        SpawnPoint spawnPoint = spawnPoints.get(spawnPointNumber);
        spawnPoint.spawn();
    }

	public void remove(GameObject gameObject) {
		this.toBeRemoved.add(gameObject);
	}

	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public ArrayList<SpawnPoint> getSpawnPoints() {
		return spawnPoints;
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}
	
	public ArrayList<Monster> getMonsters()
	{
		return monsters;
	}
	
	private <Type extends GameObject> void for_all(ArrayList<Type> arrayList, GameObjectMethod gameObjectMethod) {
		if (!arrayList.isEmpty()) {
			for (Type gameObject : arrayList) {
				gameObjectMethod.apply((GameObject) gameObject);
			}
		}
	}

	public void render(SpriteBatch batch) {
		GameObjectMethod Rendering = new GameObjectMethod() {
			@Override
			public void apply(GameObject gameObject) {
				if (gameObject != null) gameObject.render(batch);
			}
		};
		try {
			for_all(tiles, Rendering);
			for_all(spawnPoints, Rendering);
			for_all(monsters, Rendering);
			for_all(projectiles, Rendering);
		} catch (ConcurrentModificationException c) {
			MainGame.log(c.getMessage());
		}
		this.player.render(batch);
	}

	public void render(ShapeRenderer shapeRenderer) {
		GameObjectMethod shapeRendering = new GameObjectMethod() {
			@Override
			public void apply(GameObject gameObject) {
				if (gameObject != null) {
					gameObject.shapeRender(shapeRenderer);
				}
			}
		};
		for_all(tiles, shapeRendering);
		for_all(spawnPoints, shapeRendering);
		for_all(monsters, shapeRendering);
		for_all(projectiles, shapeRendering);
		player.shapeRender(shapeRenderer);
	}

	public void updateAll() {
		GameObjectMethod updating = new GameObjectMethod() {
			@Override
			public void apply(GameObject gameObject) {
				if (gameObject != null) gameObject.update();
			}
		};
		for_all(tiles, updating);
		for_all(spawnPoints, updating);
		for_all(monsters, updating);
		for_all(projectiles, updating);
		this.player.update();
		for (GameObject gameObject : this.toBeRemoved) {
			if (gameObject instanceof Monster) {
				monsters.remove(gameObject);
			} else if (gameObject instanceof Projectile) {
				projectiles.remove(gameObject);
			}
		}
	}
	
	public void dispose()
	{
		this.player.dispose();
		for(Projectile projectile : this.projectiles)
		{
			projectile.dispose();
		}
		for(Monster monster : this.monsters)
		{
			monster.dispose();
		}
		for(Tile tile : this.tiles)
		{
			tile.dispose();
		}
	}

}
