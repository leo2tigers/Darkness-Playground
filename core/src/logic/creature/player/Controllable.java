/**
 * This Controllable interface represents GameObject's controllable properties.
 */
package logic.creature.player;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public interface Controllable {
	class Key{
		boolean left = false, 
				right = false, 
				up = false, 
				down = false, 
				attack = false;
	}
	final Key key = new Key();
	final Key prev_key = new Key();
	
	default public void setupInputListener(Actor actor) {
		actor.addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if (keycode == Input.Keys.LEFT) {
					key.left = true;
				}
				if (keycode == Input.Keys.RIGHT) {
					key.right = true;
				}
				if (keycode == Input.Keys.UP) {
					key.up = true;
				}
				if (keycode == Input.Keys.DOWN) {
					key.down = true;
				}
				if (keycode == Input.Keys.Z) {
					key.attack = true;
				}
				return true;
			}
		});
	}
	
	default public void nextKey() {
		prev_key.left = key.left;
		prev_key.right = key.right;
		prev_key.up = key.up;
		prev_key.down = key.down;
		key.left = false;
		key.right = false; 
		key.up = false; 
		key.down = false;
	}
}
