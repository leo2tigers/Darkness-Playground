package logic;

public abstract class GameObject {
    public double positionX;
    public double positionY;

    public static double getDistance(GameObject first, GameObject second) {
        return Math.sqrt(
                Math.pow(first.positionX - second.positionX, 2) + Math.pow(first.positionY - second.positionY, 2)
        );
    }
    GameMap map;
    abstract public void update();
}
