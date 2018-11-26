package com.darknessplayground.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.darknessplayground.game.screen.MainMenu;

public class DarknessPlayground extends Game {
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
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
}