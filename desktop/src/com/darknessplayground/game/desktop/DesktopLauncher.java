package com.darknessplayground.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.darknessplayground.game.DarknessPlayground;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Darkness' Playground";
		config.foregroundFPS = 60;
		config.width = DarknessPlayground.WIDTH;
		config.height = DarknessPlayground.HEIGHT;
		config.resizable = false;
		new LwjglApplication(new DarknessPlayground(), config);
	}
}
