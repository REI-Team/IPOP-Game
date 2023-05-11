package com.reiteam.ipopgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Logs {
    public static ArrayList<String> logs;
    public BitmapFont font;
    private float stateTime=0;

    public Logs(){
        logs=new ArrayList<String>();
        font = new BitmapFont();
    }

    public void render(SpriteBatch batch){
        String finalString = "";
        stateTime+=Gdx.graphics.getDeltaTime();
        if(stateTime>5){
            removeLast();
            stateTime=0;
        }
        for (int i =logs.size()-1;i>=0;i--){
            finalString+=logs.get(i);
        }
        font.draw(batch, finalString, 10, 100);
    }
    private void removeLast(){
        if(logs.size()>0) {
            logs.remove(0);
        }
    }
    public void add(String type,String text){
        logs.add("["+type+"] " + text + "\n");
    };

}