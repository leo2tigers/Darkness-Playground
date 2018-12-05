package com.darknessplayground.game.desktop;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import logic.creature.player.Player;

public class PlayerMonitor implements ApplicationListener {
    private SpriteBatch batch;
    private Player owner;
    private BitmapFont owner_status;

    PlayerMonitor(Player owner) {
        super();
        this.owner = owner;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        owner_status = new BitmapFont();
        owner_status.setColor(Color.RED);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        batch.begin();
        owner_status.draw(batch,
                "Player = " + owner.name +
                    "\nHealth = " + owner.health + " / " + owner.getMaxHealth() +
                    "\nAlive = " + owner.isAlive() +
                    "\nGun = " + owner.gun,
                200, 200);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
