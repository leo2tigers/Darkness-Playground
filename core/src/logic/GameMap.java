package logic;

import logic.creature.monster.Monster;
import logic.creature.monster.Spawnable;
import logic.creature.player.Player;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameMap {

    private final ArrayList<SpawnPoint> spawnPoints = new ArrayList<SpawnPoint>();
    private final ArrayList<Tile> tiles = new ArrayList<Tile>();
    private final ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
    private final ArrayList<Monster> monsters = new ArrayList<Monster>();
    public final ArrayList<GameObject> to_be_removed = new ArrayList<>();

    public Player player;

    public GameMap() {
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
        projectiles.add(projectile);
        projectile.map = this;
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
		this.to_be_removed.add(gameObject);
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
	
	interface GameObjectMethod {
		abstract void apply(GameObject gameObject);
	}
	private static <Type extends GameObject> void for_all(ArrayList<Type> arrayList, GameObjectMethod gameObjectMethod) {
		for (Type gameObject : arrayList) {
			gameObjectMethod.apply((GameObject) gameObject);
		}
	}

	public void render(SpriteBatch batch) {
		GameObjectMethod Rendering = new GameObjectMethod() {
			@Override
			public void apply(GameObject gameObject) {
				gameObject.render(batch);
			}
		};
		for_all(tiles, Rendering);
		for_all(spawnPoints, Rendering);
		for_all(monsters, Rendering);
		for_all(projectiles, Rendering);
		this.player.render(batch);
	}

	public void render(ShapeRenderer shapeRenderer) {
		GameObjectMethod shapeRendering = new GameObjectMethod() {
			@Override
			public void apply(GameObject gameObject) {
				gameObject.shapeRender(shapeRenderer);
			}
		};
		for_all(tiles, shapeRendering);
		for_all(spawnPoints, shapeRendering);
		for_all(monsters, shapeRendering);
		for_all(projectiles, shapeRendering);
		player.shapeRender(shapeRenderer);
	}

	private final static GameObjectMethod updating = new GameObjectMethod() {
		@Override
		public void apply(GameObject gameObject) {
			gameObject.update();
		}
	};
	public void updateAll() {
		for_all(tiles, updating);
		for_all(monsters, updating);
		for_all(projectiles, updating);
		for (GameObject gameObject : this.to_be_removed) {
			if (gameObject instanceof Monster) {
				monsters.remove(gameObject);
			} else if (gameObject instanceof Projectile) {
				projectiles.remove(gameObject);
			}
		}
		for_all(tiles, updating);
		for_all(spawnPoints, updating);
		for_all(monsters, updating);
		for_all(projectiles, updating);
		this.player.update();
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
