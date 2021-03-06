package logic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class URect extends GameObject {
	protected double width;
	protected double height;
	private Color color = null;

	public URect(double positionX, double positionY, double width, double height) {
		super(positionX, positionY);
		this.width = width;
		this.height = height;
	}

	public URect(double positionX, double positionY, double width, double height, Color color) {
		super(positionX, positionY);
		this.width = width;
		this.height = height;
		this.color = color;
	}

	public void translate(double x, double y) {
		positionX += x;
		positionY += y;
	}

	public boolean overlap(URect other) {
		if (other == null) {
			return false;
		}
		double dx = Math.abs((positionX + width / 2) - (other.positionX + other.width / 2));
		double dy = Math.abs((positionY + height / 2) - (other.positionY + other.height / 2));
		return (dx <= width / 2 + other.width / 2) && (dy <= height / 2 + other.height / 2);
	}

	@Override
	public String toString() {
		return "URect " + " ( " + positionX + " , " + positionY + " ) : width = " + width + ", height = " + height;
	}

	@Override
	public void update() {
		return;
	}

	@Override
	public void shapeRender(ShapeRenderer shapeRenderer) {
		if (color != null) {
			shapeRenderer.rect((float) positionX, (float) positionY, (float) width, (float) height, color, color, color,
					color);
			shapeRenderer.circle((float) (positionX + width / 2), (float) (positionY + height / 2), 5);
		}
	}

	@Override
	public void render(SpriteBatch batch) {
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
}
