package com.reiteam.ipopgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.reiteam.ipopgame.UI.UIManager;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private UIManager uimanager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		uimanager = new UIManager();
		//img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		uimanager.render();
		//batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
