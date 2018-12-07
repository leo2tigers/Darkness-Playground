package com.darknessplayground.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.darknessplayground.game.DarknessPlayground;

import logic.creature.player.Pistol;
import logic.creature.player.Player;

public class TestLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Darkness' Playground - test";
		config.foregroundFPS = 60;
		config.width = DarknessPlayground.WIDTH;
		config.height = DarknessPlayground.HEIGHT;
		config.resizable = false;
		Player player = new Player("player one", 0.0, 0.0, new Pistol());
		new LwjglApplication(new PlayerMonitor(player), config);
	}
}
