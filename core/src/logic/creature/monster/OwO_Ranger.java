package logic.creature.monster;

import com.badlogic.gdx.graphics.Texture;

import logic.Projectile;

public class OwO_Ranger extends OwO {

	public OwO_Ranger(String name, double positionX, double positionY) {
		super("Ranger-" + name, 2, positionX, positionY, new Texture("Monsters/Ranged OwO/new_ranged_owo.png"), new Texture("Monsters/Ranged OwO/new_ranged_owo_attack.png"), new Texture("Monsters/Ranged OwO/new_ranged_owo_jump.png"));
	}

	@Override
	protected void attack_prepare() {
	}
	
	@Override
	protected void attackMethod() {
		int damage = 1;
		map.add(new Projectile(positionX, positionY, 
				               /*width*/10, /*height*/10, 
				               orientation, /*speed*/25, 
				               /*lifetime*/50, damage, Projectile.TO_PLAYER, 
				               new Texture("Monsters/Ranged OwO/normal_projectile.png")));
	}
}
