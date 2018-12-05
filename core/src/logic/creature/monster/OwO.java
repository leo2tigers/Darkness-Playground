package logic.creature.monster;

import com.badlogic.gdx.graphics.Texture;

import logic.*;

public class OwO extends Monster {
	
	protected Texture atkImg;
	protected Texture jumpImg;
	
    public OwO(String name, int maxHealth, double positionX, double positionY, Texture img) {
        super("OwO-" + name, 1, positionX, positionY, img);
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
