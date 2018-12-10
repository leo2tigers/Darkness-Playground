package com.darknessplayground.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

import logic.creature.player.Player;

public class WeaponUI {
	
	private Texture normalUI;
	private Texture noAmmoUI;
	private Texture reloadingUI;
	private BitmapFont font;

	public WeaponUI() {
		this.font = new BitmapFont(Gdx.files.internal("Fonts/Agency_FB_32px.fnt"));
		this.normalUI = new Texture("UI/weapon/weapon-ui_normal.png");
		this.noAmmoUI = new Texture("UI/weapon/weapon-ui_no-ammo.png");
		this.reloadingUI = new Texture("UI/weapon/weapon-ui_reloading.png");
	}
	
	public void render(Player player, SpriteBatch batch)
	{
		{
			int ammo = player.gun.getAmmo();
			int maxAmmo = player.gun.getMaxAmmo();
			int positionX = Gdx.graphics.getWidth() - this.normalUI.getWidth() - 10;
			int positionY = 10;
			if(player.gun.isReloading())
			{
				batch.draw(reloadingUI, positionX, positionY);
				GlyphLayout ammoDisplay = new GlyphLayout(font, ammo + "/" + maxAmmo, new Color(0, 1, 1, 1), 50, Align.left, false);
				this.font.draw(batch, ammoDisplay, Gdx.graphics.getWidth() - 70 - ammoDisplay.width, 10 + 40);
			}
			else if(ammo <= 0)
			{
				batch.draw(noAmmoUI, positionX, positionY);
				GlyphLayout ammoDisplay = new GlyphLayout(font, ammo + "/" + maxAmmo, Color.RED, 50, Align.left, false);
				this.font.draw(batch, ammoDisplay, Gdx.graphics.getWidth() - 70 - ammoDisplay.width, 10 + 40); 
			}
			else
			{
				batch.draw(normalUI, positionX, positionY);
				GlyphLayout ammoDisplay = new GlyphLayout(font, ammo + "/" + maxAmmo, new Color(0.70196f, 0.70196f, 0.70196f, 1), 50, Align.left, false);
				this.font.draw(batch, ammoDisplay, Gdx.graphics.getWidth() - 70 - ammoDisplay.width, 10 + 40);
			}
		}
	}
	
	public void dispose()
	{
		this.font.dispose();
		this.normalUI.dispose();
		this.noAmmoUI.dispose();
		this.reloadingUI.dispose();
	}

}
