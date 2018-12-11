package logic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tile extends URect {

	private double realPositionY;
	private static int idCount = 0;
	private int id;
	private Texture tilePic;

	public final TileType type;

	public Tile(TileType type, double positionX, double positionY, double width, double height, Texture tilePic) {
		super(positionX, positionY + height - 20, width, 20, Color.GREEN);
		this.realPositionY = positionY;
		this.type = type;
		this.tilePic = tilePic;
		id = idCount++;
	}

	@Override
	public String toString() {
		return "Tile" + id + " Type : " + type + " : ( " + (positionX - width / 2) + " , " + (positionY + height / 2)
				+ " ) - ( " + (positionX + width / 2) + " , " + (positionY + height / 2) + " )";
	}

	public void render(SpriteBatch batch) {
		batch.draw(this.tilePic, (float) (this.positionX), (float) (this.realPositionY));
	}

	public void dispose() {
		this.tilePic.dispose();
	}
}
