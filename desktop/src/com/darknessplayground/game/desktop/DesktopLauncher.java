package com.darknessplayground.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.darknessplayground.game.DarknessPlayground;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 400;
		config.height = 400;
		config.resizable = false;
		new LwjglApplication(new HelloWorld(), config);
	}
}
