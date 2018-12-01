package com.darknessplayground.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.darknessplayground.game.DarknessPlayground;

public class MainMenu implements Screen {
	
	private static final int BUTTON_POSITION_X = 190;
	private static final int PLAY_BUTTON_POSITION_Y = 430;
	private static final int EXIT_BUTTON_POSITION_Y = 530;
	private static final float PLAY_BUTTON_SCALE = (7/32);
	private static final float EXIT_BUTTON_SCALE = (6/22);
	
	private DarknessPlayground game;
	
	private Texture bg;
	private Texture gameTitle;
	private Texture playButtonActive;
	private Texture playButtonInActive;
	private Texture exitButtonActive;
	private Texture exitButtonInActive;

	public MainMenu(DarknessPlayground game) {
		// TODO Auto-generated constructor stub
		this.game = game;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		String bgPath = ClassLoader.getSystemResource("MainMenuBG.png").getPath();
		this.bg = new Texture(bgPath.substring(bgPath.lastIndexOf("/")+1));
		this.gameTitle = new Texture("Menu/GameTitle.png");
		this.playButtonActive = new Texture("Menu/PlayButtonActive.png");
		this.playButtonInActive = new Texture("Menu/PlayButtonInactive.png");
		this.exitButtonActive = new Texture("Menu/ExitButtonActive.png");
		this.exitButtonInActive = new Texture("Menu/ExitButtonInactive.png");
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.22f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.buttonInputHandler();
		
		this.game.batch.begin();
		if(this.isOnPlayBtn())
		{
			this.game.batch.draw(playButtonActive, BUTTON_POSITION_X, PLAY_BUTTON_POSITION_Y, this.playButtonActive.getWidth()*PLAY_BUTTON_SCALE, this.playButtonActive.getHeight()*PLAY_BUTTON_SCALE);
		}
		else
		{
			this.game.batch.draw(playButtonInActive, BUTTON_POSITION_X, PLAY_BUTTON_POSITION_Y, this.playButtonInActive.getWidth()*PLAY_BUTTON_SCALE, this.playButtonInActive.getHeight()*PLAY_BUTTON_SCALE);
		}
		
		if(this.isOnExitBtn())
		{
			this.game.batch.draw(exitButtonActive, BUTTON_POSITION_X, EXIT_BUTTON_POSITION_Y, this.exitButtonActive.getWidth()*EXIT_BUTTON_SCALE, this.exitButtonActive.getHeight()*EXIT_BUTTON_SCALE);
		}
		else
		{
			this.game.batch.draw(exitButtonInActive, BUTTON_POSITION_X, EXIT_BUTTON_POSITION_Y, this.exitButtonInActive.getWidth()*EXIT_BUTTON_SCALE, this.exitButtonInActive.getHeight()*EXIT_BUTTON_SCALE);
		}
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
		this.bg.dispose();
		this.gameTitle.dispose();
		this.playButtonActive.dispose();
		this.playButtonInActive.dispose();
		this.exitButtonActive.dispose();
		this.exitButtonInActive.dispose();
	}
	
	private void buttonInputHandler()
	{
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
		{
			if(this.isOnPlayBtn())
			{
				this.dispose();
				this.game.toGame();
			}
			if(this.isOnExitBtn())
			{
				this.dispose();
				Gdx.app.exit();
			}
		}
	}
	
	private boolean isOnPlayBtn()
	{
		if(Gdx.input.getX() >= BUTTON_POSITION_X && 
			Gdx.input.getX() <= BUTTON_POSITION_X + this.playButtonActive.getWidth()*PLAY_BUTTON_SCALE &&
			Gdx.input.getY() >= DarknessPlayground.HEIGHT - PLAY_BUTTON_POSITION_Y - this.playButtonActive.getHeight()*PLAY_BUTTON_SCALE &&
			Gdx.input.getY() <= DarknessPlayground.HEIGHT - PLAY_BUTTON_POSITION_Y)
		{
			return true;
		}
		
		return false;
	}
	
	private boolean isOnExitBtn()
	{
		if(Gdx.input.getX() >= BUTTON_POSITION_X && 
				Gdx.input.getX() <= BUTTON_POSITION_X + this.exitButtonActive.getWidth()*EXIT_BUTTON_SCALE &&
				Gdx.input.getY() >= DarknessPlayground.HEIGHT - EXIT_BUTTON_POSITION_Y - this.exitButtonActive.getHeight()*EXIT_BUTTON_SCALE &&
				Gdx.input.getY() <= DarknessPlayground.HEIGHT - EXIT_BUTTON_POSITION_Y)
		{
			return true;
		}
			
		return false;
	}

}
