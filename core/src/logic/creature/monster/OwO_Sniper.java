package logic.creature.monster;

import com.badlogic.gdx.graphics.Texture;

import logic.Projectile;

public class OwO_Sniper extends OwO {

	public OwO_Sniper(String name, double positionX, double positionY) {
		super("Sniper-" + name, 5, positionX, positionY, new Texture("Monsters/Sniper OwO/new_sniper_owo.png"), new Texture("Monsters/Sniper OwO/new_sniper_owo_attack.png"), new Texture("Monsters/Sniper OwO/new_sniper_owo_jump.png"));
	}

	@Override
	protected void attack_prepare() {
	}
	
	@Override
	protected void attackMethod() {
		int damage = 10;
		map.add(new Projectile(positionX, positionY, 
				               /*width*/20, /*height*/10, 
				               orientation, /*speed*/50, 
				               /*lifetime*/50, damage, Projectile.TO_PLAYER, 
				               new Texture("Monsters/Sniper OwO/sniper_projectile.png")));
	}
}
