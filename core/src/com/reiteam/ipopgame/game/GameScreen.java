package com.reiteam.ipopgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.reiteam.ipopgame.MainGame;
import com.reiteam.ipopgame.game.Components.Image;
import com.reiteam.ipopgame.game.Components.MarqueeLabel;
import com.reiteam.ipopgame.game.Components.Player;
import com.reiteam.ipopgame.game.Components.Totem;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen {
    private Stage mainStage;
    public static Image img;
    public static Player player;
    final int IDLE=0, UP=1, DOWN=2, LEFT=3, RIGHT=4;
    Rectangle up, down, left, right, fire;
    private ArrayList<Totem> totems;
    public GameScreen(){
        mainStage=new Stage(MainGame.viewport);
        img=new Image(new Texture("gamebg.png"),0, 0,2,2);
        player = new Player(new Texture("character.png"),500,500);
        totems = new ArrayList<Totem>();
        mainStage.addActor(img);
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        up = new Rectangle(0, screenHeight*2/3, screenWidth, screenHeight/3);
        down = new Rectangle(0, 0, screenWidth, screenHeight/3);
        left = new Rectangle(0, 0, screenWidth/3, screenHeight);
        right = new Rectangle(screenWidth*2/3, 0, screenWidth/3, screenHeight);
        mainStage.addActor(player);
    }

    public void update(){
        playerMovement();
        img.setX(img.getWidth()-player.getX());
        img.setY(img.getHeight()-player.getY());
        mainStage.act(Gdx.graphics.getDeltaTime());
        for (Totem totem: totems) {
            totem.checkCollision(player.getCollider());
        }
    }

    public void draw(){
        mainStage.draw();
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
    public void generateTotems(){
        totems.clear();
        String[] availableGrades = {"Sistemes microinformàtics i xarxes","Administració de sistemes informàtics en xarxa","Desenvolupament d’aplicacions multiplataforma","Desenvolupament d’aplicacions web",
                                    "Gestió administrativa","Administració i finances","Assistència a la direcció","Electromecànica de vehicles automòbils","Automoció",
                                    "Manteniment electromecànics","Mecatrònica industrial","Mecanització","Programació de la producció en fabricació mecànica","Gestió de l’aigua"};
        Random rand = new Random();
        for (int i = 0; i <= 5; i++) {
            Gdx.app.log("Etiqueta", String.valueOf(img.getWidth()+1));
            int randX = rand.nextInt(1280*2);
            int randY = rand.nextInt(720*2);
            Totem t = new Totem(randX,randY, MainGame.grade,100);
            totems.add(t);
            mainStage.addActor(t);
        }
        for (int i = 0; i <= 5; i++) {
            Gdx.app.log("Etiqueta", String.valueOf(img.getWidth()+1));
            int randX = rand.nextInt(1280*2);
            int randY = rand.nextInt(720*2);
            int gIndex = rand.nextInt(availableGrades.length);
            Totem t = new Totem(randX,randY,availableGrades[gIndex],100);
            totems.add(t);
            mainStage.addActor(t);
        }
    }
}
