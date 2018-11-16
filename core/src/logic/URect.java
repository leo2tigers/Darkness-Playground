package logic;

public class URect {

    double positionX, positionY;
    double width, height;

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
        //TODO URect's overlap() method
        return false;
    }
}
