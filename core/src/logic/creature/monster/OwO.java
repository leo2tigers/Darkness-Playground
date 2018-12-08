package logic.creature.monster;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import logic.GameMap;
import logic.Meth;
import logic.SpawnPoint;
import logic.URect;
import logic.creature.player.Player;

public class OwO extends Monster {
	

	static String img_path_stand = "Monsters/Normal OwO/new_owo.png";
	static String img_path_jump = "Monsters/Normal OwO/new_owo_jump.png";
	
    public OwO(GameMap map, String name, double positionX, double positionY) {
        super(map, "OwO-" + name, 1, positionX, positionY, null);
        this.setHitBox(0, 0, 100, 100);
        this.setMovementBox(0, -5, 100, 10);
        setImg(img_path_stand);
    }
    
    public OwO(GameMap map, String name,int maxHealth, double positionX, double positionY, Texture img, Texture atkImg, Texture jumpImg) {
        super(map, "OwO-" + name, maxHealth, positionX, positionY, img);
        this.setHitBox(0, 0, 100, 100);
        this.setMovementBox(0, -5, 100, 10);
        setImg(img_path_stand);
    }
    
    @Override
    protected void attack_prepare() {}
    
    @Override
    protected void attackMethod() {
    	URect damageBox = new URect(positionX, positionY, 50, 50);
    	if (damageBox.overlap(map.player.hitBox)) {
    		map.player.getHit(1);
    	}
    }

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(this.img, (float) positionX, (float)positionY);
		
	}
	
	@Override
	public void grantXp(Player player)
	{
		player.addXp(30);
	}
	
	public static Spawnable spawnable = new Spawnable() {
		@Override
		public Monster spawn(SpawnPoint spawnPoint) {
			return new OwO(spawnPoint.map, "from_spawn_point_01", Meth.center_random(spawnPoint.getX(), spawnPoint.spawnWidth), spawnPoint.getY());
		}
	};
}
