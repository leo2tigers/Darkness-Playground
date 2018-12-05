package logic.creature.monster;

import com.badlogic.gdx.graphics.Texture;

public enum MonsterType {
    //TODO MonsterType(?)
    OwO ("OwO", 1, new Texture("Monsters/Normal OwO/new_owo.png")),
	RANGED_OwO ("Ranged OwO", 2, new Texture("Monsters/Ranged OwO/new_ranged_owo.png"));

    public final String TypeName;
    public final int maxHealth;
    public final Texture img;

    MonsterType(String typeName, int maxHealth, Texture img) {
        this.TypeName = typeName;
        this.maxHealth = maxHealth;
        this.img = img;
    }
}
