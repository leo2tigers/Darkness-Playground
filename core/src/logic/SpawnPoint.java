package logic;

import java.util.Date;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import logic.creature.monster.MonsterType;

public class SpawnPoint extends GameObject {
    private int spawnCount = 0;
    private int spawnDelay = 2000;
    private boolean spawnable = false;
    private Date lastSpawnDate = new Date();
    private MonsterType monsterType;
     
    public  double spawnWidth = 100;

    public SpawnPoint(MonsterType monsterType, int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.monsterType = monsterType;
    }

    public void spawn() {
		this.map.add(monsterType.spawnable.spawn(this));
		this.spawnCount += 1;
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
