package logic.creature.monster;

import com.badlogic.gdx.graphics.Texture;

import logic.*;

public class OwO extends Monster {
	

	static String img_path_stand = ClassLoader.getSystemResource("Monsters/Normal OwO/new_owo.png").getPath();
	static String img_path_jump = ClassLoader.getSystemResource("Monsters/Normal OwO/new_owo_jump.png").getPath();
	
    public OwO(String name, double positionX, double positionY) {
        super("OwO-" + name, 1, positionX, positionY);
    }
    
    public OwO(String name,int maxHealth, double positionX, double positionY, Texture img, Texture atkImg, Texture jumpImg) {
        super("OwO-" + name, maxHealth, positionX, positionY, img);
        this.atkImg = atkImg;
        this.jumpImg = jumpImg;
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
