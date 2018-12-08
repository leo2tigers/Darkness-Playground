package com.darknessplayground.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.darknessplayground.game.DarknessPlayground;

public class GameOver implements Screen {
	
	private static final int BACK_TO_MENU_POSITION_Y = 450;
	
	private DarknessPlayground game;
	
	private Texture backgroundImage;
	private Texture gameOverText;
	private Texture backToMenuBtn;
	private Texture backToMenuBtnActive;
	private BitmapFont finalScoreFont;
	
	private int finalScore;
	
	public GameOver(DarknessPlayground game, int score) {
		this.game = game;
		this.backgroundImage = new Texture("playground_background.png");
		this.gameOverText = new Texture("GameOverScreen/GameOverText.png");
		this.backToMenuBtn = new Texture("GameOverScreen/BacktoMenuBtn.png");
		this.backToMenuBtnActive = new Texture("GameOverScreen/BacktoMenuBtnActive.png");
		this.finalScoreFont = new BitmapFont(Gdx.files.internal("Fonts/Agency_FB_40px.fnt"));
		this.finalScore = score;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.22f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		handleInput();
		
		GlyphLayout finalScoreRendering = new GlyphLayout(finalScoreFont, "Final Score : " + this.finalScore);
		
		this.game.batch.begin();
		this.game.batch.draw(gameOverText, Gdx.graphics.getWidth()/2 - this.gameOverText.getWidth()/2, 150);
		this.finalScoreFont.draw(this.game.batch, finalScoreRendering, Gdx.graphics.getWidth()/2 - this.gameOverText.getWidth()/2, 250);
		if(this.isOnBackToMenuButton()) {
			this.game.batch.draw(backToMenuBtnActive, Gdx.graphics.getWidth()/2 - this.gameOverText.getWidth()/2, BACK_TO_MENU_POSITION_Y);
		}
		else {
			this.game.batch.draw(backToMenuBtn, Gdx.graphics.getWidth()/2 - this.gameOverText.getWidth()/2, BACK_TO_MENU_POSITION_Y);
		}
		this.game.batch.end();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

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
		this.backgroundImage.dispose();
		this.backToMenuBtn.dispose();
		this.backToMenuBtnActive.dispose();
		this.gameOverText.dispose();
		this.finalScoreFont.dispose();

	}
	
	private void handleInput()
	{
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			if(isOnBackToMenuButton()) {
				this.dispose();
				this.game.toMainMenu();
			}
		}
	}
	
	private boolean isOnBackToMenuButton()
	{
		int centerLine = Gdx.graphics.getWidth()/2;
		int screenHeight = Gdx.graphics.getHeight();
		return (Gdx.input.getX() >= centerLine - this.backToMenuBtn.getWidth()/2 && Gdx.input.getX() <= centerLine + this.backToMenuBtn.getWidth()/2 &&
				Gdx.input.getY() >= screenHeight - BACK_TO_MENU_POSITION_Y - this.backToMenuBtn.getHeight() && Gdx.input.getY() <= screenHeight - BACK_TO_MENU_POSITION_Y);
	}

}
