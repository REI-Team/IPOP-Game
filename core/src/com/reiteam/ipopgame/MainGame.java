package com.reiteam.ipopgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.reiteam.ipopgame.UI.UIManager;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private UIManager uimanager;
	private OrthographicCamera camera;
	public static Viewport viewport;
	public static final int[] res = {1280,720};
	private Animation<TextureRegion> running;
	private float stateTime = 0;
	
	@Override
	public void create () {
		camera = new OrthographicCamera(res[0], res[1]);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();
		viewport = new FitViewport(res[0], res[1], camera); // Creating a viewport for the ui manager
		uimanager = new UIManager();
		batch = new SpriteBatch();
		img = new Texture("gamebg.png");
		//Player TEST, Make a player class
		Texture player = new Texture("character.png");;
		TextureRegion runningFrame[] = new TextureRegion[1];
		runningFrame[0] = new TextureRegion(player,458,0,463,557);
		running = new Animation<TextureRegion>(0.08f,runningFrame);
	}

	@Override
	public void render () {
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		uimanager.render(); //Rendering UI
		// TODO improove this, only for testing
		if(UIManager.activeScreen==""){
			stateTime += Gdx.graphics.getDeltaTime();
			batch.draw(img, 0, 0);
			TextureRegion playerFrame = running.getKeyFrame(stateTime,true);
			batch.draw(playerFrame, 500,400,0, 0,playerFrame.getRegionWidth(),playerFrame.getRegionHeight(),0.09f,0.1f,0);
		}
		batch.end();
	}

	@Override
	public void dispose () {

		batch.dispose();
		img.dispose();
	}
	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = 1280;
		camera.viewportHeight = 720;
		camera.update();
	}
}
