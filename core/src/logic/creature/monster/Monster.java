package logic.creature.monster;

import com.badlogic.gdx.graphics.Texture;

import logic.GameMap;
import logic.SpawnPoint;
import logic.creature.Creature;
import logic.creature.player.Player;

public abstract class Monster extends Creature{

    public Monster(GameMap map, String name, int maxHealth, double positionX, double positionY, Texture img) {
        super(map, name, maxHealth, positionX, positionY, img);
    }

    @Override
    protected void attack_prepare() {
    }

    @Override
    protected void attackMethod() {

    }
    
    @Override
    public void getHit(int damage) {
        setHealth(getHealth() - (damage - armour > 0 ? damage - armour : 0));
        if(getHealth() <= 0)
        {
        	this.map.remove(this);
        }
    }
    
    abstract public void grantXp(Player player);
    
    public static Spawnable spawnable;
}
