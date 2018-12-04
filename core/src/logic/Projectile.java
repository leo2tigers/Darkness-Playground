package logic;

import logic.monster.Monster;

public class Projectile extends GameObject {
    private double orientation;
    private double speed;
    private int damage;
    private URect damageBox;

    public Projectile(double positionX, double positionY, double width, double height, double orientation, double speed, int damage) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.orientation = orientation;
        this.speed = speed;
        this.damage = damage;
        this.damageBox = new URect(positionX, positionY, width, height);
    }

    @Override
    public void update() {
        positionX += speed*orientation;

        Monster nearest = null;
        double distance = 720.0;
        damageBox.translate(speed*orientation, 0.0);
        for (GameObject monster : map.gameObjects) {
            if (monster instanceof Monster && damageBox.overlap(((Monster) monster).hitBox)) {
                if (getDistance(this, monster) <= distance) {
                    nearest = (Monster) monster;
                }
            }
        }
        if (nearest != null) {
            nearest.getHit(damage);
        }
    }
}
