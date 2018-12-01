package com.darknessplayground.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.darknessplayground.game.DarknessPlayground;

public class MainGame implements Screen {
	
	private DarknessPlayground game;

	public MainGame(DarknessPlayground game) {
		// TODO Auto-generated constructor stub
		this.game = game;
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.22f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.game.batch.begin();
		
		this.game.batch.end();

	}

	@Override
	public void resize(int width, int height) {
		// Game Window is not resizable, so left this blank.

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	
	

}
