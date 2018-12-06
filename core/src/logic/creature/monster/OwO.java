package logic.creature.monster;

import logic.*;

public class OwO extends Monster {
	
	static String img_path_stand = ClassLoader.getSystemResource("Monsters/Normal OwO/new_owo.png").getPath();
	static String img_path_jump = ClassLoader.getSystemResource("Monsters/Normal OwO/new_owo_jump.png").getPath();
	
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
