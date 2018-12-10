package logic.creature.monster;

import com.badlogic.gdx.graphics.Texture;
import logic.creature.monster.OwO;

public enum MonsterType {
    OwO_NORMAL ("OwO", 
    	        1, 
    	        new Texture("Monsters/Normal OwO/new_owo.png"), 
    	        OwO.spawnable),
	OwO_RANGER ("Ranged OwO", 
			    2, 
			    new Texture("Monsters/Ranged OwO/new_ranged_owo.png"), 
			    OwO_Ranger.spawnable),
	OwO_SNIPER ("Sniper OwO", 
			    5, 
			    new Texture("Monsters/Sniper OwO/new_sniper_owo.png"), 
			    OwO_Sniper.spawnable);

    public final String TypeName;
    public final int maxHealth;
    public final Texture img;
    public final Spawnable spawnable;

    MonsterType(String typeName, int maxHealth, Texture img, Spawnable spawnable) {
        this.TypeName = typeName;
        this.maxHealth = maxHealth;
        this.img = img;
        this.spawnable = spawnable;
    }
}
