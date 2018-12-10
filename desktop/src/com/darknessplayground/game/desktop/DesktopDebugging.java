package com.darknessplayground.game.desktop;

import java.util.ArrayList;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.darknessplayground.game.DarknessPlayground;
import com.darknessplayground.game.Debugging;

public class DesktopDebugging {
	
	static ArrayList<String> debug_log = new ArrayList<>();
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Darkness' Playground";
		config.foregroundFPS = 60;
		config.width = DarknessPlayground.WIDTH;
		config.height = DarknessPlayground.HEIGHT;
		config.resizable = false;
		try {
			new LwjglApplication(new Debugging(debug_log), config);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			for (String log : debug_log) {
				System.out.println(log);
			}
		}
	}
}
