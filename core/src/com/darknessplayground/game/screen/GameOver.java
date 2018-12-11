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

	private static final int BACK_TO_MENU_POSITION_Y = 150;
	private static final float BACK_TO_MENU_DISPLAY_WIDTH = 477.27f;
	private static final float BACK_TO_MENU_DISPLAY_HEIGHT = 100;
	private static final float GAME_OVER_TEXT_DISPLAY_WIDTH = 593.18f;

	private DarknessPlayground game;

	private Texture gameOverText;
	private Texture backToMenuBtn;
	private Texture backToMenuBtnActive;
	private BitmapFont finalScoreFont;

	private int finalScore;
	private float stateTime;

	public GameOver(DarknessPlayground game, int score) {
		this.game = game;
		this.gameOverText = new Texture("GameOverScreen/GameOverText.png");
		this.backToMenuBtn = new Texture("GameOverScreen/BacktoMenuBtn.png");
		this.backToMenuBtnActive = new Texture("GameOverScreen/BacktoMenuBtnActive.png");
		this.finalScoreFont = new BitmapFont(Gdx.files.internal("Fonts/Agency_FB_60px.fnt"));
		this.finalScore = score;
		this.stateTime = 0;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float dt) {
		this.stateTime += dt;

		Gdx.gl.glClearColor(0.1f, 0.1f, 0.22f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		handleInput();

		GlyphLayout finalScoreRendering = new GlyphLayout(finalScoreFont, "Final Score : " + this.finalScore);

		this.game.batch.begin();
		this.game.batch.draw(gameOverText, Gdx.graphics.getWidth() / 2 - GAME_OVER_TEXT_DISPLAY_WIDTH / 2, 470,
				GAME_OVER_TEXT_DISPLAY_WIDTH, 150);
		this.finalScoreFont.draw(this.game.batch, finalScoreRendering,
				Gdx.graphics.getWidth() / 2 - finalScoreRendering.width / 2, 350);
		if (this.stateTime >= 1) {
			if (this.isOnBackToMenuButton()) {
				this.game.batch.draw(backToMenuBtnActive, Gdx.graphics.getWidth() / 2 - BACK_TO_MENU_DISPLAY_WIDTH / 2,
						BACK_TO_MENU_POSITION_Y, BACK_TO_MENU_DISPLAY_WIDTH, BACK_TO_MENU_DISPLAY_HEIGHT);
			} else {
				this.game.batch.draw(backToMenuBtn, Gdx.graphics.getWidth() / 2 - BACK_TO_MENU_DISPLAY_WIDTH / 2,
						BACK_TO_MENU_POSITION_Y, BACK_TO_MENU_DISPLAY_WIDTH, BACK_TO_MENU_DISPLAY_HEIGHT);
			}
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
		this.backToMenuBtn.dispose();
		this.backToMenuBtnActive.dispose();
		this.gameOverText.dispose();
		this.finalScoreFont.dispose();

	}

	private void handleInput() {
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			if (isOnBackToMenuButton()) {
				this.dispose();
				this.game.toMainMenu();
			}
		}
	}

	private boolean isOnBackToMenuButton() {
		int centerLine = Gdx.graphics.getWidth() / 2;
		int screenHeight = Gdx.graphics.getHeight();
		return (Gdx.input.getX() >= centerLine - BACK_TO_MENU_DISPLAY_WIDTH / 2
				&& Gdx.input.getX() <= centerLine + BACK_TO_MENU_DISPLAY_WIDTH / 2
				&& Gdx.input.getY() >= screenHeight - BACK_TO_MENU_POSITION_Y - BACK_TO_MENU_DISPLAY_HEIGHT
				&& Gdx.input.getY() <= screenHeight - BACK_TO_MENU_POSITION_Y);
	}

}
