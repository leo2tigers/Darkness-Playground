package logic.creature.player;

public abstract class PlayerStats {
	public static final int MAX_HEALTH = 5;
	public static final int ARMOUR = 0;
	public static final double MOVEMENT_SPEED = 5;
	public static final double JUMPING_SPEED = 50;
	
	abstract public  class HitBox {
		public static final double WIDTH = 100;
		public static final double HEIGHT = 150;
		public static final double RELATIVE_X = 0;
		public static final double RELATIVE_Y = 0;
	}
	abstract public  class MovementBox {
		public static final double WIDTH = 100;
		public static final double HEIGHT = 20;
		public static final double RELATIVE_X = 0;
		public static final double RELATIVE_Y = -10;
	}
}
