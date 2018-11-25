package com.darknessplayground.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import log.Log;

public class DarknessPlayground extends Game {
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	private SpriteBatch batch;
    private BitmapFont font;
	private String h = "Hello World!";
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
		font = new BitmapFont();
		font.setColor(Color.BLUE);
        Log.begin();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if((Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))) {
			h = "fuck you!";
		}

		batch.begin();
		//batch.draw(img, 0, 0);
		font.draw(batch, h, 200, 200);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
		font.dispose();
	}
}
