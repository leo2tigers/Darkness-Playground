package com.darknessplayground.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.darknessplayground.game.DarknessPlayground;

import logic.GameMap;
import logic.GameObject;
import logic.Tile;
import logic.creature.player.Pistol;
import logic.creature.player.Player;

public class MainGame implements Screen {
	
	private DarknessPlayground game;
	
	private GameMap map;
	private Player player;
	private BitmapFont debugFont;
	
	private boolean infoDebugActive;
	private boolean rectDebugActive;
	private float timeSurvived;
	private float timeForPassiveXp;

	public MainGame(DarknessPlayground game) {
		// TODO Auto-generated constructor stub
		this.game = game;
		this.map = new GameMap();
		this.player = new Player(this.map, "player_one", 400, 100, new Pistol()); //To be implemented
		this.debugFont = new BitmapFont();
		this.infoDebugActive = false;
		this.rectDebugActive = false;
		this.timeSurvived = 0;
		this.timeForPassiveXp = 0;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		this.map.setPlayer(this.player);
	}

	@Override
	public void render(float dt) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.22f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
		{
			this.dispose();
			this.game.toMainMenu();
		}
		
		this.timeSurvived += dt;
		this.timeForPassiveXp += dt;
		if(this.timeForPassiveXp >= 1)
		{
			this.timeForPassiveXp--;
			this.player.xpFromTime(4 + (((int)this.timeSurvived - (int)this.timeSurvived%60)) / 60 * 2);
		}
		
		handleInput();
		String information = ">> " + this.player.toString() + 
				             "\n    - osition = " + this.player.getPosition() + 
				             "\n    - Attackable = " + this.player.attackable +
				             "\n    - Gun = " + this.player.gun +
				             "\n    - Status = " + this.player.status +
				             "\n";
		information += ">> Tiles : \n";
		for (Tile tile : this.map.getTiles()) {
			information += "    - " + tile.toString();
		}
		information += "\n>> GameObjects : \n";
		for (GameObject gameObject : this.map.gameObjects) {
			information += "    - " + gameObject.toString();
		}
		GlyphLayout label = new GlyphLayout(this.debugFont, information);
        
        this.map.updateAll();
		
		this.game.batch.begin();
		this.map.render(this.game.batch);
		if(this.infoDebugActive) this.debugFont.draw(this.game.batch, label, 0, Gdx.graphics.getHeight() - 15);
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
		// TODO Auto-generated method stub

	}
	
	private void handleInput()
	{
		if(Gdx.input.isKeyJustPressed(Keys.UP))
		{
			this.player.jump();
		}
		else if(Gdx.input.isKeyJustPressed(Keys.DOWN))
		{
			//
		}
		
		if(Gdx.input.isKeyPressed(Keys.LEFT))
		{
			this.player.moveLeft();
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			this.player.moveRight();
		}
		if(Gdx.input.isKeyPressed(Keys.SPACE))
		{
			//this.player.attack();
			this.player.inCombat();
			this.player.attack();
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

}
