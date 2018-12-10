package com.darknessplayground.game.screen;

import java.util.ArrayList;
import java.util.Date;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.darknessplayground.game.DarknessPlayground;
import com.darknessplayground.game.Debugging;
import com.darknessplayground.game.ui.HpBar;
import com.darknessplayground.game.ui.WeaponUI;

import logic.GameMap;
import logic.Projectile;
import logic.SpawnPoint;
import logic.Tile;
import logic.creature.monster.*;
import logic.creature.player.Pistol;
import logic.creature.player.Player;

public class MainGame implements Screen {
	
	private static final float PAUSED_RESUME_BUTTON_Y = 345;
	private static final float PAUSED_EXIT_BUTTON_Y = 218;
	
	private static String status = "normal";

	private DarknessPlayground game;
	
	private GameMap map;
	private Player player;
	private BitmapFont debugFont;
	private BitmapFont noticeFont;
	private HpBar hpBar;
	private WeaponUI weaponUI;
	private Music bgm;
	
	private Texture pauseScreen;
	private Texture pauseScreenResumeBtnActive;
	private Texture pauseScreenResumeBtnInactive;
	private Texture pauseScreenExitBtnActive;
	private Texture pauseScreenExitBtnInactive;
	
	private boolean infoDebugActive;
	private boolean rectDebugActive;
	private float timeSurvived;
	private float timeForPassiveXp;
	private String noticeText;
	private float noticeShowTime;
	private boolean isPausing = false;

	private Texture bg;

	private static String information;
	private static ArrayList<String> game_log = new ArrayList<>();
	private static int log_height = 30;

	public MainGame(DarknessPlayground game) {
		this.game = game;
		this.map = new GameMap();
		this.player = new Player(this.map, "player_one", 400, 100, new Pistol(), this);
		this.debugFont = new BitmapFont();
		this.noticeFont = new BitmapFont(Gdx.files.internal("Fonts/Agency_FB_32px.fnt"));
		this.infoDebugActive = false;
		this.rectDebugActive = false;
		this.timeSurvived = 0;
		this.timeForPassiveXp = 0;
		this.noticeShowTime = 0;
		this.noticeText = "";
		this.isPausing = false;
	}
	
	private void setupMap() {
		log("setup map");
		log("setup tiles");
		this.map.add(new Tile(Tile.Type.FLOOR, 0, 0, 1280, 100, new Texture("Tiles/floor.png")));
		this.map.add(new Tile(Tile.Type.PLATFORM, 0, 250, 500, 50, new Texture("Tiles/tile10.png")));
		this.map.add(new Tile(Tile.Type.PLATFORM, 400, 400, 400, 50, new Texture("Tiles/tile8.png")));
		log("setup player");
		this.map.setPlayer(this.player);
		log("setup monsters");
		//this.map.add(new OwO_Sniper(this.map, "aplha-tester", 100, 100));
		//this.map.add(new OwO_Ranger(this.map, "aplha-tester", 100, 100));
		log("setup spawnpoints");
		this.map.addSpawnPoint(new SpawnPoint(MonsterType.OwO_NORMAL, 100, 100, 0.5));
		//this.map.addSpawnPoint(new SpawnPoint(MonsterType.OwO_NORMAL, 100, 500, 0.5));
		//this.map.addSpawnPoint(new SpawnPoint(MonsterType.OwO_RANGER, 1000, 100, 0.5));
		//this.map.addSpawnPoint(new SpawnPoint(MonsterType.OwO_RANGER, 600, 500, 0.5));
		//this.map.addSpawnPoint(new SpawnPoint(MonsterType.OwO_SNIPER, 1000, 500, 0.5, 5000));
	}

	@Override
	public void show() {
		this.bg = new Texture("playground_background.png");
		this.weaponUI = new WeaponUI();
		this.hpBar = new HpBar();
		this.setupMap();
		this.bgm = Gdx.audio.newMusic(Gdx.files.internal("BGM/Game.mp3"));
		this.bgm.setLooping(true);
		this.bgm.setVolume(0.6f);
		this.bgm.play();
		this.pauseScreen = new Texture("PauseScreen/Pause Screen.png");
		this.pauseScreenResumeBtnActive = new Texture("PauseScreen/ResumeBtnActive.png");
		this.pauseScreenResumeBtnInactive = new Texture("PauseScreen/ResumeBtn.png");
		this.pauseScreenExitBtnActive = new Texture("PauseScreen/ExitBtnActive.png");
		this.pauseScreenExitBtnInactive = new Texture("PauseScreen/ExitBtn.png");
	}

	@Override
	public void render(float dt) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.22f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(this.isPausing) {
			dt = 0;
		}
		
		this.handleNoticeShow(dt);
		
		this.handlePassiveXp(dt);
		this.player.setShootingAnimationDelay(Math.max(0, this.player.getShootingAnimationDelay() - dt));
		if(this.player.getShootingAnimationDelay() <= 0.15f && this.player.getAnimationState() == 5)
		{
			this.player.setAnimationState(6);
		}
		
		// -- information for debugging --
		information = ">> Game Status : " + status +
				             "\n>> " + this.player.toString() + 
				             "\n    - position = " + this.player.getPosition() + 
				             "\n    - Speed = " + this.player.speedX + " , " + this.player.speedY +
				             "\n    - Attackable = " + this.player.attackable +
				             "\n    - Gun = " + this.player.gun +
				             "\n    - Status = " + this.player.status +
				             "\n    - Current Tile = " + this.player.current_tile +
				             "\n";
		information += ">> Tiles : \n";
		for (Tile tile : this.map.getTiles()) {
			information += "\n    - " + tile.toString();
		}
		information += "\n>> SpawnPoints : ";
		if (this.map.getSpawnPoints().isEmpty()) {
			information += " NONE";
		} else {
			for (SpawnPoint spawnPoint : this.map.getSpawnPoints()) {
				information += "\n    - " + spawnPoint;
			}
		}
		information += "\n>> Monsters " + this.map.getMonsters().size() + " : ";
		if (this.map.getMonsters().isEmpty()) {
			information += " NONE";
		} else {
			for (Monster monster : this.map.getMonsters()) {
				information += "\n    - " + monster.toString() + " , Position : " + monster.getPosition();
			}
		}
		information += "\n>> Projectiles : ";
		if (this.map.getProjectiles().isEmpty()) {
			information += " NONE";
		} else {
			for (Projectile projectile : this.map.getProjectiles()) {
				if (projectile != null) information += "\n    - " + projectile.toString();
			}
		}
		GlyphLayout label = new GlyphLayout(this.debugFont, information);
		GlyphLayout log = new GlyphLayout(this.debugFont, "");
		log = new GlyphLayout(this.debugFont, "" + get_log());
		GlyphLayout notice = new GlyphLayout(noticeFont, noticeText, Color.RED, 50, Align.left, false);
		GlyphLayout score = new GlyphLayout(noticeFont, "Score : " + this.player.getXp());
        // -- information for debugging --
		
		if(!this.isPausing) {
			handleInput(dt);
			this.map.updateAll();
		}
		
		if(this.player.getHealth() <= 0) {
			this.bgm.stop();
			this.dispose();
			this.game.gameOver(this.player.getXp());
		}
        
		this.game.batch.begin();
		this.game.batch.draw(bg, 0, 0, DarknessPlayground.WIDTH, DarknessPlayground.HEIGHT);
		this.map.render(game.batch);
		this.weaponUI.render(this.player, game.batch);
		this.hpBar.render(this.player, game.batch);
		this.noticeFont.draw(game.batch, score, 10, Gdx.graphics.getHeight() - 10);
		this.noticeFont.draw(game.batch, notice, Gdx.graphics.getWidth()/2 - notice.width/2, notice.height+10);
		if(this.infoDebugActive) {
			try {
				this.debugFont.draw(game.batch, log, 750, Gdx.graphics.getHeight() - 15);
				this.debugFont.draw(game.batch, label, 0, Gdx.graphics.getHeight() - 15);
			} catch (NullPointerException npe) {
				MainGame.log("log's Spritebatch error");
			}
		}
		this.game.batch.end();
		if(this.isPausing)
			Gdx.gl.glEnable(GL20.GL_BLEND);
		{	
		    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		    this.game.shapeRenderer.begin(ShapeType.Filled);
		    this.game.shapeRenderer.setColor(new Color(0, 0, 0, 0.5f));
		    this.game.shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		    this.game.shapeRenderer.end();
		    
		    Gdx.gl.glDisable(GL20.GL_BLEND);
		    this.game.batch.begin();
		    this.renderPausedScreen(Gdx.graphics.getDeltaTime());
		    this.game.batch.end();
		}

		game.shapeRenderer.begin(ShapeType.Line);
		if(this.rectDebugActive) this.map.render(game.shapeRenderer);
		game.shapeRenderer.end();
	}
	
	@Override
	public void resize(int width, int height) {/*Game Window cannot be resized, so left this blank.*/}

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
		this.debugFont.dispose();
		this.noticeFont.dispose();
		this.player.dispose();
		this.map.dispose();
		this.hpBar.dispose();
		this.weaponUI.dispose();
		this.bgm.dispose();
		this.pauseScreen.dispose();
		this.pauseScreenResumeBtnActive.dispose();
		this.pauseScreenResumeBtnInactive.dispose();
		this.pauseScreenExitBtnActive.dispose();
		this.pauseScreenExitBtnInactive.dispose();
	}
	
	private void handleInput(float dt)
	{
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
		{
			this.bgm.pause();
			this.isPausing = true;
			/*this.bgm.stop();
			this.dispose();
			this.game.gameOver(this.player.getXp());*/
		}
		if(Gdx.input.isKeyJustPressed(Keys.UP))
		{
			this.player.jump();
		}
		else if(Gdx.input.isKeyJustPressed(Keys.DOWN))
		{
			if (this.player.current_tile != null)
				if (this.player.current_tile.type != Tile.Type.FLOOR) this.player.jump_down();
		}
		
		if(!Gdx.input.isKeyPressed(Keys.LEFT) && !Gdx.input.isKeyPressed(Keys.RIGHT) && this.player.getShootingAnimationDelay() <= 0)
		{
			this.player.setAnimationState(0);
			this.player.setTimeRunning(0);
		}
		else if(Gdx.input.isKeyPressed(Keys.LEFT) && Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			this.player.setAnimationState(0);
			this.player.setTimeRunning(0);
		}
		else if(Gdx.input.isKeyPressed(Keys.LEFT))
		{
			this.player.moveLeft();
			this.player.setTimeRunning(this.player.getTimeRunning() + dt);
			if(this.player.getShootingAnimationDelay() > 0)
			{
				
			}
			else if(this.player.jumping)
			{
				this.player.setAnimationState(2);
			}
			else
			{
				this.player.calculateAnimationState();
			}
		}
		else if(Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			this.player.moveRight();
			this.player.setTimeRunning(this.player.getTimeRunning() + dt);
			if(this.player.getShootingAnimationDelay() > 0)
			{
				
			}
			else if(this.player.jumping)
			{
				this.player.setAnimationState(2);
			}
			else
			{
				this.player.calculateAnimationState();
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.SPACE))
		{
			//this.player.attack();
			if(this.player.gun.getAmmo() > 0) {
				this.player.setAnimationState(5);
				this.player.setShootingAnimationDelay(0.2f);
			}
			this.player.inCombat();
			this.player.attack();
		}
		if(Gdx.input.isKeyJustPressed(Keys.R))
		{
			this.player.gun.reload();
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.F1))
		{
			this.infoDebugActive = (this.infoDebugActive) ? false : true;
		}
		if(Gdx.input.isKeyJustPressed(Keys.F2))
		{
			this.rectDebugActive = (this.rectDebugActive) ? false : true;
		}
	}

	private void renderPausedScreen(float dt)
	{
		this.game.batch.draw(pauseScreen, Gdx.graphics.getWidth()/2 - this.pauseScreen.getWidth()/2, Gdx.graphics.getHeight()/2 - this.pauseScreen.getHeight()/2);
		handlePausedInput(dt);
		if(this.isOnPausedResumeButton()) {
			this.game.batch.draw(pauseScreenResumeBtnActive, Gdx.graphics.getWidth()/2 - this.pauseScreenResumeBtnActive.getWidth()/2, PAUSED_RESUME_BUTTON_Y);
		}
		else {
			this.game.batch.draw(pauseScreenResumeBtnInactive, Gdx.graphics.getWidth()/2 - this.pauseScreenResumeBtnActive.getWidth()/2, PAUSED_RESUME_BUTTON_Y);
		}
		if(this.isOnPausedExitButton()) {
			this.game.batch.draw(pauseScreenExitBtnActive, Gdx.graphics.getWidth()/2 - this.pauseScreenExitBtnActive.getWidth()/2, PAUSED_EXIT_BUTTON_Y);
		}
		else {
			this.game.batch.draw(pauseScreenExitBtnInactive, Gdx.graphics.getWidth()/2 - this.pauseScreenExitBtnActive.getWidth()/2, PAUSED_EXIT_BUTTON_Y);
		}
	}
	
	private void handlePausedInput(float dt)
	{
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			if(isOnPausedResumeButton()) {
				this.isPausing = false;
				this.bgm.play();
			}
			else if(isOnPausedExitButton()) {
				this.bgm.stop();
				this.dispose();
				this.game.gameOver(this.player.getXp());
			}
		}
	}
	
	private boolean isOnPausedResumeButton()
	{
		return (Gdx.input.getX() >= Gdx.graphics.getWidth()/2 - this.pauseScreenResumeBtnActive.getWidth()/2 &&
		   Gdx.input.getX() <= Gdx.graphics.getWidth()/2 + this.pauseScreenResumeBtnActive.getWidth()/2 &&
		   Gdx.input.getY() >= Gdx.graphics.getHeight() - PAUSED_RESUME_BUTTON_Y - this.pauseScreenResumeBtnActive.getHeight() &&
		   Gdx.input.getY() <= Gdx.graphics.getHeight() - PAUSED_RESUME_BUTTON_Y);
	}
	
	private boolean isOnPausedExitButton()
	{
		return (Gdx.input.getX() >= Gdx.graphics.getWidth()/2 - this.pauseScreenExitBtnActive.getWidth()/2 &&
				Gdx.input.getX() <= Gdx.graphics.getWidth()/2 + this.pauseScreenExitBtnActive.getWidth()/2 &&
				Gdx.input.getY() >= Gdx.graphics.getHeight() - PAUSED_EXIT_BUTTON_Y - this.pauseScreenExitBtnActive.getHeight() &&
				Gdx.input.getY() <= Gdx.graphics.getHeight() - PAUSED_EXIT_BUTTON_Y);
	}
	
	public static void sendStatus(String string) {
		status = string;
	}
	
	private void handlePassiveXp(float dt)
	{
		this.timeSurvived += dt;
		this.timeForPassiveXp += dt;
		if(this.timeForPassiveXp >= 1)
		{
			this.timeForPassiveXp--;
			this.player.xpFromTime(4 + (((int)this.timeSurvived - (int)this.timeSurvived%60)) / 60 * 2);
		}
	}
	
	private void handleNoticeShow(float dt)
	{
		if(this.noticeShowTime > 0)
		{
			this.noticeShowTime -= dt;
		}
		else
		{
			this.noticeShowTime = 0;
		}
		if(this.noticeShowTime <= 0)
		{
			this.noticeText = "";
		}
	}	
	
	public void showNotice(String notice)
	{
		this.noticeShowTime = 2;
		this.noticeText = notice;
	}
	
	public static void log(String string) {
		game_log.add(string);
		Debugging.send_log(string);
	}

	private String get_log() {
		String str = "log :\n";
		for (int i = game_log.size(); i > 0 && i > game_log.size() - log_height; --i) {
			str += i + " >>    " + game_log.get(i - 1) + "\n";
		}
		return str;
	}
}
