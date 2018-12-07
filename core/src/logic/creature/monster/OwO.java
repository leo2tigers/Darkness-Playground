package logic.creature.monster;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import logic.*;
import logic.creature.player.Player;

public class OwO extends Monster {
	

	static String img_path_stand = "Monsters/Normal OwO/new_owo.png";
	static String img_path_jump = "Monsters/Normal OwO/new_owo_jump.png";
	
    public OwO(GameMap map, String name, double positionX, double positionY) {
        super(map, "OwO-" + name, 1, positionX, positionY, null);
    }
    
    public OwO(GameMap map, String name,int maxHealth, double positionX, double positionY, Texture img, Texture atkImg, Texture jumpImg) {
        super(map, "OwO-" + name, maxHealth, positionX, positionY, img);
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

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(this.img, this.getX(), this.getY());
		
	}
	
	@Override
	public void grantXp(Player player)
	{
		player.addXp(30);
	}
}
