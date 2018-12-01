package com.darknessplayground.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.darknessplayground.game.screen.*;

public class DarknessPlayground extends Game {
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
//	public static float dt = Gdx.graphics.getDeltaTime();
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainMenu(this));
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
}