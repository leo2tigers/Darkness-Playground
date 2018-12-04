package com.darknessplayground.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.darknessplayground.game.DarknessPlayground;
import logic.player.Pistol;
import logic.player.Player;

public class TestLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Darkness' Playground - test";
		config.foregroundFPS = 60;
		config.width = DarknessPlayground.WIDTH;
		config.height = DarknessPlayground.HEIGHT;
		config.resizable = false;
		new LwjglApplication(new PlayerMonitor(new Player("player one", 5, 0.0, 0.0, new Pistol())), config);
	}
}
