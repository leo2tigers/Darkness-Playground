package logic;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import logic.creature.IRenderable;

public abstract class GameObject implements IRenderable {
    protected double positionX;
    protected double positionY;
    public GameMap map;
    
    public GameObject(double positionX, double positionY)
    {
    	this.positionX = positionX;
    	this.positionY = positionY;
    }
    
    public GameObject(double positionX, double positionY, GameMap map) {
    	this.positionX = positionX;
    	this.positionY = positionY;
    	this.map = map;
    }

    public static double getDistance(GameObject first, GameObject second) {
        return Math.sqrt(
                Math.pow(first.positionX - second.positionX, 2) + Math.pow(first.positionY - second.positionY, 2)
        );
    }
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
	
	public GameMap getMap()
	{
		return this.map;
	}

	public void setX(double positionX) {
		this.positionX = positionX;
	}

	public void setY(double positionY) {
		this.positionY = positionY;
	}
}
