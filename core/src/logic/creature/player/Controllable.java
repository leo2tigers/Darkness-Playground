/**
 * This Controllable interface represents GameObject's controllable properties.
 */
package logic.creature.player;

public interface Controllable {
	class Key{
		boolean left_KeyPressed = false, 
				right_KeyPressed = false, 
				up_KeyPressed = false, 
				down_KeyPressed = false;
	}
	Key keyClass = new Key();
	abstract public void keyPressed(String key);
}
