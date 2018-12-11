package com.darknessplayground.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.darknessplayground.game.screen.MainGame;

import logic.creature.player.Player;

public class HpBar {

	private Texture normalBarBorder;
	private Texture lowBarBorder;
	private Texture normalBar;
	private Texture lowBar;
	private BitmapFont hpTextFont;
	private BitmapFont currentHpFont;

	public HpBar() {
		this.hpTextFont = new BitmapFont(Gdx.files.internal("Fonts/Agency_FB_23px.fnt"));
		this.currentHpFont = new BitmapFont(Gdx.files.internal("Fonts/Agency_FB_32px.fnt"));
		this.normalBarBorder = new Texture("UI/health/bar.png");
		this.lowBarBorder = new Texture("UI/health/bar_red.png");
		this.normalBar = new Texture("UI/health/progress.png");
		this.lowBar = new Texture("UI/health/progress_red.png");
	}

	public void render(Player player, SpriteBatch batch) {
		try {
			int currentHealth = player.getHealth();
			int maxHealth = player.getMaxHealth();
			GlyphLayout hpText;
			GlyphLayout currentHpText;
			if ((float) currentHealth / (float) maxHealth <= 0.2f) {
				hpText = new GlyphLayout(this.hpTextFont, "HP", Color.RED, 50, Align.left, false);
				currentHpText = new GlyphLayout(this.currentHpFont, currentHealth + "/" + maxHealth, Color.RED, 50,
						Align.left, false);
				batch.draw(lowBarBorder, 10, 10);
				batch.draw(this.lowBar, 10 + 70, 10 + 10, (300 * ((float) currentHealth / (float) maxHealth)), 10);
			} else {
				hpText = new GlyphLayout(this.hpTextFont, "HP", new Color(0.70196f, 0.70196f, 0.70196f, 1), 50,
						Align.left, false);
				currentHpText = new GlyphLayout(this.currentHpFont, currentHealth + "/" + maxHealth,
						new Color(0.70196f, 0.70196f, 0.70196f, 1), 50, Align.left, false);
				batch.draw(normalBarBorder, 10, 10);
				batch.draw(this.normalBar, 10 + 70, 10 + 10, (300 * ((float) currentHealth / (float) maxHealth)), 10);
			}
			this.hpTextFont.draw(batch, hpText, 10 + 30, 10 + 30);
			this.currentHpFont.draw(batch, currentHpText, 10 + 380 + 10, 10 + 30);
		} catch (Exception e) {
			MainGame.log(e.getMessage());
		}
	}

	public void dispose() {
		this.hpTextFont.dispose();
		this.currentHpFont.dispose();
		this.normalBarBorder.dispose();
		this.lowBarBorder.dispose();
		this.normalBar.dispose();
		this.lowBar.dispose();
	}

}
