package test;

import logic.GameMap;
import logic.Tile;
import logic.URect;
import logic.player.Player;

public class test {
    public static void main(String[] args) {
        GameMap map = new GameMap();

        Tile tile1 = new Tile(-15, -10, 50, 20);
        Tile tile2 = new Tile(35, -10, 50, 20);
        System.out.println(tile1);
        System.out.println(tile2);
        map.addAll(tile1, tile2);

        Player player = new Player("player1", 1, 0, 50);
        map.setPlayer(player);
        player.setMovementBox(0, -40, 40, 20);
        player.setHitBox(0, 0, 50, 100);
        player.speedX = 20;

        System.out.println(player);
        System.out.println(player.movementBox);

        System.out.println("jumping ? " + player.jumping);
        player.keyPressed("RIGHT");
        System.out.println("jumping ? " + player.jumping);

        System.out.println(player.getPosition());
        System.out.println(player.current_tile);
    }
}
