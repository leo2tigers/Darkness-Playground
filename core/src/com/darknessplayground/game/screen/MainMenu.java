package com.darknessplayground.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.darknessplayground.game.DarknessPlayground;

public class MainMenu implements Screen {
	
	private DarknessPlayground game;

	private Texture img;
	private BitmapFont font;

	public MainMenu(DarknessPlayground game) {
		// TODO Auto-generated constructor stub
		this.game = game;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		this.img = new Texture("badlogic.jpg");
		this.font = new BitmapFont();
		this.font.setColor(Color.GOLD);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.22f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.game.batch.begin();
		this.game.batch.draw(img, 0, 0);
		this.font.draw(this.game.batch, "Hello World!", 500, 600);
		this.game.batch.end();

	}

	@Override
	public void resize(int width, int height) {
		// Game Window is not resizable, so left this blank.

	}

	@Override
	public void pause() {
		// Not used on Main Menu

	}

	@Override
	public void resume() {
		// Not used on Main Menu

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		img.dispose();
		font.dispose();
	}

}