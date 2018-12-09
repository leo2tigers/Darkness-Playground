package logic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tile extends URect {
	
	private double real_positionY;
	private double tile_height;
	
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
        super(positionX, positionY + height - 20, width, 20, Color.GREEN);
        this.real_positionY = positionY;
        this.tile_height = height;
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
    	batch.draw(this.tilePic, (float) (this.positionX), (float) (this.real_positionY));
    }
    
    public void dispose()
    {
    	this.tilePic.dispose();
    }
}
