package logic;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class URect extends GameObject {
    public double width, height;
    public Rectangle rectangle;

    public URect(double positionX, double positionY, double width, double height) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
        this.rectangle = new Rectangle((float)positionX, (float)positionY, (float)width, (float)height);
    }

    public void translate(double x, double y) {
        positionX += x;
        positionY += y;
        this.rectangle.setPosition((float)positionX, (float)positionY);
    }

    public boolean overlap(URect other) {
        if (other == null) {
            return false;
        }
        double dx = Math.abs(positionX - other.positionX);
        double dy = Math.abs(positionY - other.positionY);
        return (dx <= width/2 + other.width/2) && (dy <= height/2 + other.height/2);
    }

    @Override
    public String toString() {
        return "URect " +
                " ( " + positionX + " , " + positionY + " ) : width = " + width + ", height = " + height;
    }

    @Override
    public void update() {
        return;
    }

	@Override
	public void shapeRender(ShapeRenderer shapeRenderer) {
		shapeRenderer.rect((float) (positionX - width/2), (float) (positionY - height/2), (float) width, (float) height);
	}
}
