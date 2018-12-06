package logic.creature.player;

import logic.creature.Creature;

public class Monitor {
	
	private Creature creature;
	private String name_field;
	private String status_field;
	private int health_field, max_health_field;

	public Monitor(Creature creature) {
		this.creature = creature;
		this.name_field = creature.name;
		this.status_field = creature.isAlive() ? "Alive" : "Dead";
		this.health_field = creature.health;
		this.max_health_field = creature.maxHealth;
	}
	
	public void setCreature(Creature creature) {
		this.creature = creature;
		update();
	}
	
	public void update() {
		this.name_field = creature.name;
		this.status_field = creature.isAlive() ? "Alive" : "Dead";
		this.health_field = creature.health;
		this.max_health_field = creature.maxHealth;
	}

	@Override
	public String toString() {
		return "Creature : " + name_field +
			   " ( " + status_field + " ) " +
			   "\nHealth : " + health_field + " / " + max_health_field;
	}
	
	public static void main(String[] args) {
		Player player_one = new Player("player one", 0., 0.,null);
		Monitor monitor = new Monitor(player_one);
		System.out.println(player_one);
		System.out.println(monitor);
	}
}
