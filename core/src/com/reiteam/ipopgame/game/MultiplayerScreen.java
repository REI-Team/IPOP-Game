package com.reiteam.ipopgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.reiteam.ipopgame.MainGame;
import com.reiteam.ipopgame.game.Components.Image;
import com.reiteam.ipopgame.game.Components.MarqueeLabel;
import com.reiteam.ipopgame.game.Components.Player;
import com.reiteam.ipopgame.game.Components.Totem;

import java.util.ArrayList;
import java.util.Random;

public class MultiplayerScreen {
    private static MultiplayerScreen instance;
    private Stage mainStage;
    public static Image img;
    public static Player player;
    final int IDLE=0, UP=1, DOWN=2, LEFT=3, RIGHT=4;
    Rectangle up, down, left, right, fire;
    private static Music gamemusic;
    private Label.LabelStyle labelStyle;
    private Label time,success,error;
    private ServerConnection server;
    public MultiplayerScreen(){
        setupFontStyle();
        mainStage=new Stage(MainGame.viewport);
        instance=this;
        // BASIC GAME OBJECTS
        img=new Image(new Texture("gamebg.png"),0, 0,2,2);
        player = new Player(new Texture("character.png"),500,500);
        gamemusic = Gdx.audio.newMusic(Gdx.files.internal("music/game_music.mp3"));
        mainStage.addActor(img);
        // Local player movement vars
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        up = new Rectangle(0, screenHeight*2/3, screenWidth, screenHeight/3);
        down = new Rectangle(0, 0, screenWidth, screenHeight/3);
        left = new Rectangle(0, 0, screenWidth/3, screenHeight);
        right = new Rectangle(screenWidth*2/3, 0, screenWidth/3, screenHeight);
        mainStage.addActor(player);
        // INGAME LABELS
        time = createLabel("Time: ",10,690);
        success = createLabel("Success: ",10,660);
        error = createLabel("Error: ",10,630);
        mainStage.addActor(time);
        mainStage.addActor(success);
        mainStage.addActor(error);
    }
    private void setupFontStyle(){
        BitmapFont font = new BitmapFont(); // Making a instance of BitmapFont for the font
        labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.font.getData().setScale(1.5f);
        labelStyle.fontColor = Color.BLACK;
    }

    public void update(){
        playerMovement();
        img.setX(img.getWidth()-player.getX());
        img.setY(img.getHeight()-player.getY());
        mainStage.act(Gdx.graphics.getDeltaTime());
        time.setText("Time: "+String.valueOf((int) MainGame.time));
        success.setText("Success: "+String.valueOf(MainGame.success));
        error.setText("Error: "+String.valueOf(MainGame.error));
    }

    public void draw(){
        mainStage.draw();
    }

    private Label createLabel(String text,int x,int y){
        Skin skin = new Skin();
        skin.add("default", labelStyle);
        Label label = new Label(text, skin); // Making a label instance
        label.setPosition(x, y); // Center label
        label.setAlignment(Align.center);
        return label;
    }

    private void playerMovement(){
        switch (virtual_joystick_control()){
            case 0:
                player.setPlayerMode(0);
                break;
            case 1:
                player.setY(player.getY()-2);
                player.setPlayerMode(4);
                break;
            case 2:
                player.setY(player.getY()+2);
                player.setPlayerMode(3);
                break;
            case 3:
                player.setX(player.getX()-2);
                player.setPlayerMode(2);
                player.setRotation(-2);
                break;
            case 4:
                player.setX(player.getX()+2);
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
    public static void toggleMusic(boolean enable){
        if(enable){
            gamemusic.play();
        }else{
            gamemusic.stop();
        }
    }
    public void startConnection(){
        server= new ServerConnection("localhost",8888);
    }
    public static MultiplayerScreen getInstance(){
        return instance;
    }
}
