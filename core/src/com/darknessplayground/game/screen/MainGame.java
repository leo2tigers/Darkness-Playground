package com.darknessplayground.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.darknessplayground.game.DarknessPlayground;

import logic.GameMap;
import logic.Projectile;
import logic.SpawnPoint;
import logic.Tile;
import logic.creature.monster.Monster;
import logic.creature.monster.MonsterType;
import logic.creature.player.Pistol;
import logic.creature.player.Player;

public class MainGame implements Screen {
	
	private static String status = "normal";

	private DarknessPlayground game;
	
	private GameMap map;
	private Player player;
	private BitmapFont debugFont;
	private BitmapFont noticeFont;
	private Texture weaponUINormal;
	private Texture weaponUINoAmmo;
	private Texture weaponUIReloading;
	
	private boolean infoDebugActive;
	private boolean rectDebugActive;
	private float timeSurvived;
	private float timeForPassiveXp;
	private String noticeText;
	private float noticeShowTime;

	private Texture bg;

	private static String information;

	public MainGame(DarknessPlayground game) {
		this.game = game;
		this.map = new GameMap();
		this.player = new Player(this.map, "player_one", 400, 100, new Pistol(), this);
		this.debugFont = new BitmapFont();
		this.noticeFont = new BitmapFont(Gdx.files.internal("Fonts/Agency_FB_32px.fnt"));
		this.weaponUINormal = new Texture("UI/weapon/weapon-ui_normal.png");
		this.weaponUINoAmmo = new Texture("UI/weapon/weapon-ui_no-ammo.png");
		this.weaponUIReloading = new Texture("UI/weapon/weapon-ui_reloading.png");
		this.infoDebugActive = false;
		this.rectDebugActive = false;
		this.timeSurvived = 0;
		this.timeForPassiveXp = 0;
		this.noticeShowTime = 0;
		this.noticeText = "";
	}

	@Override
	public void show() {
		this.bg = new Texture("playground_background.png");
		this.map.add(new Tile(Tile.Type.FLOOR, 0, 0, 1280, 100, new Texture("Tiles/playground_floor.png")));
		this.map.setPlayer(this.player);
		this.map.addSpawnPoint(new SpawnPoint(MonsterType.OwO_NORMAL, 100, 100));
	}

	@Override
	public void render(float dt) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.22f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
		{
			this.dispose();
			this.game.toMainMenu();
		}
		
		this.handleNoticeShow(dt);
		
		this.handlePassiveXp(dt);
		this.player.setShootingAnimationDelay(Math.max(0, this.player.getShootingAnimationDelay() - dt));
		if(this.player.getShootingAnimationDelay() <= 0 && this.player.getAnimationState() == 5)
		{
			this.player.setAnimationState(6);
			this.player.setShootingAnimationDelay(0.15f);
		}
		
		handleInput(dt);
		
		// -- information for debugging --
		information = ">> Game Status : " + status +
				             "\n>> " + this.player.toString() + 
				             "\n    - position = " + this.player.getPosition() + 
				             "\n    - Speed = " + this.player.speedX + " , " + this.player.speedY +
				             "\n    - Attackable = " + this.player.attackable +
				             "\n    - Gun = " + this.player.gun +
				             "\n    - Status = " + this.player.status +
				             "\n";
		information += ">> Tiles : \n";
		for (Tile tile : this.map.getTiles()) {
			information += "    - " + tile.toString();
		}
		information += "\n>> SpawnPoints : ";
		if (this.map.getSpawnPoints().isEmpty()) {
			information += " NONE";
		} else {
			for (SpawnPoint spawnPoint : this.map.getSpawnPoints()) {
				information += "\n    - " + spawnPoint;
			}
		}
		information += "\n>> Monsters : ";
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
				information += "\n    - " + projectile.toString();
			}
		}
		GlyphLayout label = new GlyphLayout(this.debugFont, information);
		GlyphLayout notice = new GlyphLayout(noticeFont, noticeText, Color.RED, 50, Align.left, false);
        // -- information for debugging --
		
        this.map.updateAll();
        
		this.game.batch.begin();
		this.game.batch.draw(bg, 0, 0, DarknessPlayground.WIDTH, DarknessPlayground.HEIGHT);
		this.map.render(this.game.batch);
		if(this.infoDebugActive) this.debugFont.draw(this.game.batch, label, 0, Gdx.graphics.getHeight() - 15);
		this.showWeaponUI();
		this.noticeFont.draw(this.game.batch, notice, Gdx.graphics.getWidth()/2 - notice.width/2, notice.height+10);
		this.game.batch.end();

		this.game.shapeRenderer.begin(ShapeType.Line);
		if(this.rectDebugActive) this.map.render(this.game.shapeRenderer);
		this.game.shapeRenderer.end();
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
		this.weaponUINormal.dispose();
		this.weaponUINoAmmo.dispose();
		this.weaponUIReloading.dispose();
	}
	
	private void handleInput(float dt)
	{
		if(Gdx.input.isKeyJustPressed(Keys.UP))
		{
			this.player.jump();
		}
		else if(Gdx.input.isKeyJustPressed(Keys.DOWN))
		{
			//
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
			if(this.player.jumping)
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
			if(this.player.jumping)
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
			System.out.println("key pressed");
			if(this.player.gun.getAmmo() > 0) {
				this.player.setAnimationState(5);
				this.player.setShootingAnimationDelay(0.05f);
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
	
	private void showWeaponUI()
	{
		int ammo = this.player.gun.getAmmo();
		int maxAmmo = this.player.gun.getMaxAmmo();
		int positionX = Gdx.graphics.getWidth() - this.weaponUINormal.getWidth() - 10;
		int positionY = 10;
		if(this.player.gun.isReloading())
		{
			this.game.batch.draw(weaponUIReloading, positionX, positionY);
			GlyphLayout ammoDisplay = new GlyphLayout(noticeFont, ammo + "/" + maxAmmo, new Color(0, 1, 1, 1), 50, Align.left, false);
			this.noticeFont.draw(this.game.batch, ammoDisplay, Gdx.graphics.getWidth() - 70 - ammoDisplay.width, 10 + 40);
		}
		else if(ammo <= 0)
		{
			this.game.batch.draw(weaponUINoAmmo, positionX, positionY);
			GlyphLayout ammoDisplay = new GlyphLayout(noticeFont, ammo + "/" + maxAmmo, Color.RED, 50, Align.left, false);
			this.noticeFont.draw(this.game.batch, ammoDisplay, Gdx.graphics.getWidth() - 70 - ammoDisplay.width, 10 + 40); 
		}
		else
		{
			this.game.batch.draw(weaponUINormal, positionX, positionY);
			GlyphLayout ammoDisplay = new GlyphLayout(noticeFont, ammo + "/" + maxAmmo, new Color(0.70196f, 0.70196f, 0.70196f, 1), 50, Align.left, false);
			this.noticeFont.draw(this.game.batch, ammoDisplay, Gdx.graphics.getWidth() - 70 - ammoDisplay.width, 10 + 40);
		}
	}

}
