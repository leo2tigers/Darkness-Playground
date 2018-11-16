package logic;

import java.util.*;

public class GameMap {
    Player player;
    final ArrayList<Monster> monsters = new ArrayList<>();
    final ArrayList<SpawnPoint>
    final ArrayList<Tile> tiles = new ArrayList<>();

    public void setPlayer(Player player) {
        this.player = player;
        player.map = this;
    }
}
