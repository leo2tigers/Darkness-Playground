package logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tile extends URect {
	
	public static enum Type {
		FLOOR ("FLOOR"), PLATFORM ("PLATFORM");
		
		private final String string;
		Type(String string) {
			this.string = string;
		}
		
		@Override
		public String toString() {
			return this.string;
		}
	}

    private static int idCount = 0;
    private int id;
    private Texture tilePic;
    
    public final Type type;

    public Tile(Type type,double positionX, double positionY, double width, double height, Texture tilePic) {
        super(positionX + width/2, positionY + height/2, width, height);
        this.type = type;
        this.tilePic = tilePic;
        id = idCount++;
    }

    @Override
    public String toString() {
        return "Tile" + id + " Type : " + type + " : ( " + (positionX - width/2) + " , " + (positionY + height/2) + " ) - ( " + (positionX + width/2) + " , " + (positionY + height/2) + " )";
    }
    
    public void render(SpriteBatch batch)
    {
    	batch.draw(this.tilePic, (float) (this.positionX - width/2), (float) (this.positionY - height/2));
    }
}
