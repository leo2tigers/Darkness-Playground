package logic.creature.monster;

import logic.Projectile;

public class OwO_Sniper extends OwO {

	public OwO_Sniper(String name, double positionX, double positionY) {
		super("Sniper-" + name, 5, positionX, positionY);
	}

	@Override
	protected String attack_prepare() {
		return super.attack_prepare();
	}
	
	@Override
	protected void attackMethod() {
		int damage = 10;
		map.add(new Projectile(positionX, positionY, 
				               /*width*/20, /*height*/10, 
				               orientation, /*speed*/50, 
				               /*lifetime*/50, damage, Projectile.TO_PLAYER));
	}
}
