package logic.creature.monster;

import logic.*;

public class OwO extends Monster {
	
    public OwO(String name, double positionX, double positionY) {
        super("OwO-" + name, 1, positionX, positionY);
    }
    
    public OwO(String name,int health, double positionX, double positionY) {
        super("OwO-" + name, health, positionX, positionY);
    }
    
    @Override
    protected void attack_prepare() {
    }
    
    @Override
    protected void attackMethod() {
    	URect damageBox = new URect(positionX, positionY, 50, 50);
    	if (damageBox.overlap(map.player.hitBox)) {
    		map.player.getHit(1);
    	}
    }
}
