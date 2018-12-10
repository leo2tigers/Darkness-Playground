package com.darknessplayground.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.darknessplayground.game.*;

public class DesktopLauncher_Debugging {
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Darkness' Playground";
		config.foregroundFPS = 60;
		config.width = DarknessPlayground.WIDTH;
		config.height = DarknessPlayground.HEIGHT;
		config.resizable = false;
		new LwjglApplication(new Debugging(null), config);
	}
}
