package logic;

import java.util.Date;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.darknessplayground.game.screen.MainGame;

import logic.creature.monster.Monster;
import logic.creature.monster.MonsterType;

public class SpawnPoint extends GameObject {
    private int spawnCount = 0;
	private double spawnRate = 1;
    private int spawnDelay = 2500;
    private boolean spawnable = false;
    private Date startSpawnDate = new Date();
    private Date lastSpawnDate = new Date();
    private MonsterType monsterType;
     
    public  double spawnWidth = 100;

    public SpawnPoint(MonsterType monsterType, int positionX, int positionY, double spawnRate) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.monsterType = monsterType;
        this.spawnRate = spawnRate;
    }

    public SpawnPoint(MonsterType monsterType, int positionX, int positionY, double spawnRate, int spawnDelay) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.monsterType = monsterType;
		this.monsterType = monsterType;
        this.spawnRate = spawnRate;
		this.spawnDelay = spawnDelay;
	}

	public void spawn() {
    	for (int i = 0; i < (int) ((lastSpawnDate.getTime() - startSpawnDate.getTime())/this.spawnDelay*this.spawnRate); i++) {
	    	Monster monster = monsterType.spawnable.spawn(this);
	    	MainGame.log("spawn " + monster.name + " from SpawnPoint " + this.positionX + " , " + this.positionY);
			this.map.add(monster);
			this.spawnCount += 1;
    	}
    	if (spawnCount == 0) {
    		Monster monster = monsterType.spawnable.spawn(this);
	    	MainGame.log("spawn " + monster.name + " from SpawnPoint " + this.positionX + " , " + this.positionY);
			this.map.add(monster);
			this.spawnCount += 1;
    	}
		this.spawnable = false;
		this.lastSpawnDate = new Date();
    }

    @Override
    public void update() {
        if (spawnable) {
        	spawn();
        } else if ((new Date()).getTime() - lastSpawnDate.getTime() >= spawnDelay) {
        	spawnable = true;
        }
    }

	@Override
	public void shapeRender(ShapeRenderer shapeRenderer) {
		shapeRenderer.rect((float) positionX, (float) positionY, 50, 50, Color.PINK, Color.PINK, Color.PINK, Color.PINK);
	}

	@Override
	public void render(SpriteBatch batch) {
		//TODO SpawnPount.render
	}
	
	@Override
	public String toString() {
		return "SpawnPoint<" + monsterType.TypeName + "> delay = " + spawnDelay + " ms SpawnCount = " + spawnCount + " Position = ( " + positionX + " , " + positionY + " )";
	}
}
