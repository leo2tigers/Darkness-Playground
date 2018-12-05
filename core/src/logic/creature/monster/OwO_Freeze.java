package logic.creature.monster;

import logic.URect;

public class OwO_Freeze extends OwO {

	public OwO_Freeze(String name, double positionX, double positionY) {
		super("Freeze-" + name, 2, positionX, positionY);
	}
	
	@Override
	protected String attack_prepare() {
		return super.attack_prepare();
	}
	
	@Override
	protected void attackMethod() {
		URect damageBox = new URect(positionX, positionY, 200, 50);
    	if (damageBox.overlap(map.player.hitBox)) {
    		map.player.getHit(1);
    	}
	}

}
