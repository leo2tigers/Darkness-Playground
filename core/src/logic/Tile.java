package logic;

public class Tile extends URect {

    private static int idCount = 0;
    private int id;

    public Tile(double positionX, double positionY, double width, double height) {
        super(positionX, positionY, width, height);
        id = idCount++;
    }

    @Override
    public String toString() {
        return "Tile" + id + " : ( " + (positionX - width/2) + " , " + (positionY + height/2) + " ) - ( " + (positionX + width/2) + " , " + (positionY + height/2) + " )";
    }
}
