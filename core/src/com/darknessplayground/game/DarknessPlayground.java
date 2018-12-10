package com.darknessplayground.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.darknessplayground.game.screen.MainGame;
import com.darknessplayground.game.screen.MainMenu;
import com.darknessplayground.game.screen.GameOver;
import com.darknessplayground.game.screen.LoadingScreen;

public class DarknessPlayground extends Game {
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	public boolean rectDebugging = true;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		toMainMenu();
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
	
	public void gameOver(int finalScore)
	{
		this.setScreen(new GameOver(this, finalScore));
	}
	
	public void toLoadingScreen()
	{
		this.setScreen(new LoadingScreen(this));
	}
}