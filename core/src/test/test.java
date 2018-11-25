package test;

import logic.GameMap;
import logic.Tile;
import logic.URect;
import logic.player.Player;

public class test {
    public static void main(String[] args) {
        GameMap map = new GameMap();

        Tile tile1 = new Tile(-15, -5, 50, 10);
        map.addTile(tile1);
        System.out.println(tile1);
        Tile tile2 = new Tile(-15, -5, 50, 10);

        Player player = new Player("player1", 1, 0, 50);
        map.setPlayer(player);
        player.setMovementBox(new URect(0, 10, 40, 20));
        player.setHitBox(new URect(0, 50, 50, 100));
        player.speedX = 20;

        System.out.println(player);
        System.out.println(player.getPosition());
        player.keyPressed("RIGHT");
        System.out.println(player.getPosition());
        System.out.println(player.current_tile);
    }
}
