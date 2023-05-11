package com.reiteam.ipopgame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.reiteam.ipopgame.UI.UIManager;
import com.reiteam.ipopgame.game.Components.Player;
import com.reiteam.ipopgame.game.GameScreen;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.reiteam.ipopgame.game.Logs;
import com.reiteam.ipopgame.game.MultiplayerScreen;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	private UIManager uimanager;
	private OrthographicCamera camera;
	public static Viewport viewport;
	public static final int[] res = {1280,720};
	private float stateTime = 0;
	public static String grade = "";
	public  static String username = "Player";
	public static  String userID = "";
	public static int success, error;
	public static float time;
	public static boolean gameStarted=false;
	public static GameScreen gameScreen;
	public static MultiplayerScreen mpScreen;
	public static Logs gameLogs;
	@Override
	public void create () {
		success=0;
		error=0;
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		camera = new OrthographicCamera();
		camera.translate(100, 0, 0);
		gameLogs = new Logs();
		viewport = new FitViewport(res[0], res[1], camera); // Creating a viewport for the ui manager
		gameScreen = new GameScreen();
		mpScreen = new MultiplayerScreen();
		uimanager = new UIManager();
		batch = new SpriteBatch();

		resize(0,0);
	}

	@Override
	public void render () {
		if(gameStarted){
			time+=Gdx.graphics.getDeltaTime();
		}
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		uimanager.render(); //Rendering UI
		// TODO improove this, only for testing... or not
		//System.out.println(UIManager.activeScreen);
		if(UIManager.activeScreen==""){
			stateTime += Gdx.graphics.getDeltaTime();
			gameScreen.draw();
			gameScreen.update();
			//TextureRegion playerFrame = running.getKeyFrame(stateTime,true);
			//batch.draw(playerFrame, 500,400,0, 0,playerFrame.getRegionWidth(),playerFrame.getRegionHeight(),0.09f,0.1f,0);
		} else if (UIManager.activeScreen=="mpScreen") {
			stateTime += Gdx.graphics.getDeltaTime();
			mpScreen.draw();
			mpScreen.update();
		}
		if(success==5 & gameStarted){
			gameStarted=false;
			GameScreen.clearTotems();
			GameScreen.toggleMusic(false);
			UIManager.showScreen("finishGame");

		}
		gameLogs.render(batch);
		batch.end();
	}


	@Override
	public void dispose () {

		batch.dispose();
	}
	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = 1280;
		camera.viewportHeight = 720;
		camera.update();
	}
}
