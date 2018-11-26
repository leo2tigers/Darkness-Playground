package logic;

public class URect extends GameObject {

    public double positionX, positionY;
    public double width, height;

    public URect(double positionX, double positionY, double width, double height) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
    }

    public void translate(double x, double y) {
        positionX += x;
        positionY += y;
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
}
