package com.darknessplayground.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.darknessplayground.game.DarknessPlayground;

public class MainMenu implements Screen {
	
	private static final int BUTTON_POSITION_X = 190;
	private static final int PLAY_BUTTON_POSITION_Y = 290;
	private static final int EXIT_BUTTON_POSITION_Y = 190;
	private static final float PLAY_BUTTON_DISPLAY_WIDTH = 87;
	private static final float PLAY_BUTTON_DISPLAY_HEIGHT = 68;
	private static final float EXIT_BUTTON_DISPLAY_WIDTH = 75;
	private static final float EXIT_BUTTON_DISPLAY_HEIGHT = 60;
	private static final float GAME_TITLE_DISPLAY_WIDTH = 330;
	private static final float GAME_TITLE_DISPLAY_HEIGHT = 205.333f;
	
	private DarknessPlayground game;
	
	private Texture bg;
	private Texture gameTitle;
	private Texture playButtonActive;
	private Texture playButtonInActive;
	private Texture exitButtonActive;
	private Texture exitButtonInActive;
	private Music bgm;
	
	private float stateTime;

	public MainMenu(DarknessPlayground game) {
		this.game = game;
	}

	@Override
	public void show() {
		String bgPath = ClassLoader.getSystemResource("MainMenuBG.png").getPath();
		this.bg = new Texture(bgPath.substring(bgPath.lastIndexOf("/")+1));
		this.gameTitle = new Texture("Menu/GameTitle.png");
		this.playButtonActive = new Texture("Menu/PlayBtnNewActive.png");
		this.playButtonInActive = new Texture("Menu/PlayBtnNew.png");
		this.exitButtonActive = new Texture("Menu/ExitBtnNewActive.png");
		this.exitButtonInActive = new Texture("Menu/ExitBtnNew.png");
		this.bgm = Gdx.audio.newMusic(Gdx.files.internal("BGM/MainMenu.mp3"));
		this.bgm.setLooping(true);
		this.bgm.play();
	}

	@Override
	public void render(float dt) {
		this.stateTime += dt;
		
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.22f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.game.batch.begin();
		this.game.batch.draw(bg, 0, 0, DarknessPlayground.WIDTH, DarknessPlayground.HEIGHT);
		this.game.batch.draw(this.gameTitle, 190, 450, GAME_TITLE_DISPLAY_WIDTH, GAME_TITLE_DISPLAY_HEIGHT); // change from x845 = x190
		if(this.stateTime >= 1) {
			if(this.isOnPlayBtn())
			{
				this.game.batch.draw(playButtonActive, BUTTON_POSITION_X, PLAY_BUTTON_POSITION_Y, PLAY_BUTTON_DISPLAY_WIDTH, PLAY_BUTTON_DISPLAY_HEIGHT);
			}
			else
			{
				this.game.batch.draw(playButtonInActive, BUTTON_POSITION_X, PLAY_BUTTON_POSITION_Y, PLAY_BUTTON_DISPLAY_WIDTH, PLAY_BUTTON_DISPLAY_HEIGHT);
			}
			
			if(this.isOnExitBtn())
			{
				this.game.batch.draw(exitButtonActive, BUTTON_POSITION_X, EXIT_BUTTON_POSITION_Y, EXIT_BUTTON_DISPLAY_WIDTH, EXIT_BUTTON_DISPLAY_HEIGHT);
			}
			else
			{
				this.game.batch.draw(exitButtonInActive, BUTTON_POSITION_X, EXIT_BUTTON_POSITION_Y, EXIT_BUTTON_DISPLAY_WIDTH, EXIT_BUTTON_DISPLAY_HEIGHT);
			}
			
			this.buttonInputHandler();
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
		this.bgm.dispose();
	}
	
	private void buttonInputHandler()
	{
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
		{
			if(this.isOnPlayBtn())
			{
				this.bgm.stop();
				this.dispose();
				this.game.toLoadingScreen();
			}
			if(this.isOnExitBtn())
			{
				this.bgm.stop();
				this.dispose();
				Gdx.app.exit();
			}
		}
	}
	
	private boolean isOnPlayBtn()
	{
		if(Gdx.input.getX() >= BUTTON_POSITION_X && 
			Gdx.input.getX() <= BUTTON_POSITION_X + PLAY_BUTTON_DISPLAY_WIDTH &&
			Gdx.input.getY() >= DarknessPlayground.HEIGHT - PLAY_BUTTON_POSITION_Y - PLAY_BUTTON_DISPLAY_HEIGHT &&
			Gdx.input.getY() <= DarknessPlayground.HEIGHT - PLAY_BUTTON_POSITION_Y)
		{
			return true;
		}
		
		return false;
	}
	
	private boolean isOnExitBtn()
	{
		if(Gdx.input.getX() >= BUTTON_POSITION_X && 
				Gdx.input.getX() <= BUTTON_POSITION_X + PLAY_BUTTON_DISPLAY_WIDTH &&
				Gdx.input.getY() >= DarknessPlayground.HEIGHT - EXIT_BUTTON_POSITION_Y - PLAY_BUTTON_DISPLAY_HEIGHT &&
				Gdx.input.getY() <= DarknessPlayground.HEIGHT - EXIT_BUTTON_POSITION_Y)
		{
			return true;
		}
			
		return false;
	}

}
