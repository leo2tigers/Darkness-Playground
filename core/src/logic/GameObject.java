package logic;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import logic.creature.IRenderable;

public abstract class GameObject implements IRenderable {
    protected double positionX;
    protected double positionY;

    public static double getDistance(GameObject first, GameObject second) {
        return Math.sqrt(
                Math.pow(first.positionX - second.positionX, 2) + Math.pow(first.positionY - second.positionY, 2)
        );
    }
    GameMap map;
    abstract public void update();
	abstract public void shapeRender(ShapeRenderer shapeRenderer);
	
	public double getX()
	{
		return this.positionX;
	}
	
	public double getY()
	{
		return this.positionY;
	}
}
