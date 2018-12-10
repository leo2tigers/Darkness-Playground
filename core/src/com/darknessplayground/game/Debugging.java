package com.darknessplayground.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.darknessplayground.game.screen.GameOver;
import com.darknessplayground.game.screen.MainGame;
import com.darknessplayground.game.screen.MainMenu;

public class Debugging extends DarknessPlayground{
	
	static ArrayList<String> debug_log;
	
	public Debugging(ArrayList<String> debug_log) {
		super();
		this.debug_log = debug_log;
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		toGame();
		//GameOver(0);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	
	public void toMainMenu()
	{
		this.setScreen(new MainMenu(this));
	}
	
	public void toGame()
	{
		this.setScreen(new MainGame(this));
	}
	
	public void GameOver(int finalScore)
	{
		this.setScreen(new GameOver(this, finalScore));
	}
	
	public static void send_log(String string) {
		debug_log.add(string);
	}
}