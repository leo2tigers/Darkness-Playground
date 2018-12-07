package logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tile extends URect {

    private static int idCount = 0;
    private int id;
    private Texture tilePic;

    public Tile(double positionX, double positionY, double width, double height, Texture tilePic) {
        super(positionX, positionY, width, height);
        this.tilePic = tilePic;
        id = idCount++;
    }

    @Override
    public String toString() {
        return "Tile" + id + " : ( " + (positionX - width/2) + " , " + (positionY + height/2) + " ) - ( " + (positionX + width/2) + " , " + (positionY + height/2) + " )";
    }
    
    public void render(SpriteBatch batch)
    {
    	batch.draw(this.tilePic, 0, 0);
    }
}
