package com.darknessplayground.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.darknessplayground.game.DarknessPlayground;

public class LoadingScreen implements Screen {
	
	private DarknessPlayground game;
	
	private BitmapFont font;
	private GlyphLayout loadingText;
	
	private float stateTime;

	public LoadingScreen(DarknessPlayground game) {
		this.game = game;
		this.font = new BitmapFont(Gdx.files.internal("Fonts/Agency_FB_40px.fnt"));
		this.stateTime = 0;
	}

	@Override
	public void show() {
		this.loadingText = new GlyphLayout(this.font, "Loading...");

	}

	@Override
	public void render(float dt) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.22f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
		
		this.game.batch.begin();
		this.font.draw(this.game.batch, this.loadingText, Gdx.graphics.getWidth()/2 - this.loadingText.width/2, 140 + this.loadingText.height);
		this.game.batch.end();
		this.stateTime += dt;
		if(this.stateTime >= 2)
		{
			this.dispose();
			this.game.toGame();
		}

	}

	@Override
	public void resize(int width, int height) {
		// Unused since the game window is not resizable

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		this.font.dispose();

	}

}
