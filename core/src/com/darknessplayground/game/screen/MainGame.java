package com.darknessplayground.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.darknessplayground.game.DarknessPlayground;

import logic.*;
import logic.creature.player.*;

public class MainGame implements Screen {
	
	private DarknessPlayground game;
	
	private GameMap map;
	private Player player;

	public MainGame(DarknessPlayground game) {
		// TODO Auto-generated constructor stub
		this.game = game;
		this.map = new GameMap();
		this.player = new Player("player one", 0, 0, new Pistol()); //To be implemented
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		this.map.setPlayer(this.player);

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.22f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
		{
			this.dispose();
			this.game.toMainMenu();
		}
		
		this.game.shapeRenderer.begin(ShapeType.Line);
		this.map.render(this.game.shapeRenderer);
		this.game.shapeRenderer.end();
		
		this.game.batch.begin();
		this.map.render(this.game.batch);
		this.game.batch.end();

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
	
	

}
