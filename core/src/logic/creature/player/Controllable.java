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
	final Key keyClass = new Key();
	default public void resetKey() {
		keyClass.left_KeyPressed = false;
		keyClass.right_KeyPressed = false; 
		keyClass.up_KeyPressed = false; 
		keyClass.down_KeyPressed = false;
	}
	abstract public void keyPressed(String key);
}
