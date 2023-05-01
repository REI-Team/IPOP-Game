package com.reiteam.ipopgame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.reiteam.ipopgame.UI.UIManager;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private Player player;
	private UIManager uimanager;
	private OrthographicCamera camera;
	public static Viewport viewport;
	public static final int[] res = {1280,720};
	private float stateTime = 0;
	public static String grade = "";
	final int IDLE=0, UP=1, DOWN=2, LEFT=3, RIGHT=4;
	Rectangle up, down, left, right, fire;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Texture txt =new Texture("character.png");
		player = new Player(txt,-500,-500);
		camera = new OrthographicCamera();
		camera.translate(100, 0, 0);
		viewport = new FitViewport(res[0], res[1], camera); // Creating a viewport for the ui manager
		uimanager = new UIManager();
		batch = new SpriteBatch();
		img = new Texture("gamebg.png");
		//Player TEST, Make a player class
		// facilities per calcular el "touch"
		int screenWidth = Gdx.graphics.getWidth();
		int screenHeight = Gdx.graphics.getHeight();
		up = new Rectangle(0, screenHeight*2/3, screenWidth, screenHeight/3);
		down = new Rectangle(0, 0, screenWidth, screenHeight/3);
		left = new Rectangle(0, 0, screenWidth/3, screenHeight);
		right = new Rectangle(screenWidth*2/3, 0, screenWidth/3, screenHeight);
		resize(0,0);
	}

	@Override
	public void render () {
		camera.update();
		playerMovement();
		batch.setProjectionMatrix(camera.combined);
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		uimanager.render(); //Rendering UI
		// TODO improove this, only for testing
		//System.out.println(UIManager.activeScreen);
		if(UIManager.activeScreen==""){
			stateTime += Gdx.graphics.getDeltaTime();
			batch.draw(img, player.getX(), player.getY(),img.getWidth()*2,img.getHeight()*2);
			player.render(batch);
			//TextureRegion playerFrame = running.getKeyFrame(stateTime,true);
			//batch.draw(playerFrame, 500,400,0, 0,playerFrame.getRegionWidth(),playerFrame.getRegionHeight(),0.09f,0.1f,0);
		}
		batch.end();
	}
	private void playerMovement(){
		switch (virtual_joystick_control()){
			case 0:
				player.setPlayerMode(0);
				break;
			case 1:
				player.setY(player.getY()+2);
				player.setPlayerMode(4);
				break;
			case 2:
				player.setY(player.getY()-2);
				player.setPlayerMode(3);
				break;
			case 3:
				player.setX(player.getX()+2);
				player.setPlayerMode(2);
				player.setRotation(-2);
				break;
			case 4:
				player.setX(player.getX()-2);
				player.setPlayerMode(1);
				player.setRotation(2);
				break;
		}
	}
	protected int virtual_joystick_control() {
		// iterar per multitouch
		// cada "i" és un possible "touch" d'un dit a la pantalla
		for(int i=0;i<10;i++)
			if (Gdx.input.isTouched(i)) {
				Vector3 touchPos = new Vector3();
				touchPos.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
				// traducció de coordenades reals (depen del dispositiu) a 800x480
				//game.camera.unproject(touchPos);
				if (up.contains(touchPos.x, touchPos.y)) {
					return UP;
				} else if (down.contains(touchPos.x, touchPos.y)) {
					return DOWN;
				} else if (left.contains(touchPos.x, touchPos.y)) {
					return LEFT;
				} else if (right.contains(touchPos.x, touchPos.y)) {
					return RIGHT;
				}
			}
		return IDLE;
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
