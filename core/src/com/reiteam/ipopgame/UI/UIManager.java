package com.reiteam.ipopgame.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.HashMap;
import java.util.Map;

public class UIManager {
    private static Map<String,UIScreen> screens;
    public static String activeScreen = "";


    public UIManager(){
        screens = new HashMap<String,UIScreen>();
        screens.put("mainScreen",new MainMenu());
        screens.put("Ranking",new Ranking());
        screens.put("ChooseGrade",new ChooseGrade());
        showScreen("mainScreen");
    }

    public void render(){
        // Rendering the active screen
        //System.out.println(UIManager.activeScreen);
        if(activeScreen!="") {
            screens.get(activeScreen).getStage().act(Gdx.graphics.getDeltaTime());
            screens.get(activeScreen).getStage().draw();
        }
    }
    public static void showScreen(String screenName){
        activeScreen=screenName;
        if(!screenName.equals("")){
            Gdx.input.setInputProcessor(screens.get(screenName).getStage());
        }else{
            Gdx.input.setInputProcessor(null);
        }
    }
}
