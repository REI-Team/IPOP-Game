package com.reiteam.ipopgame.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.HashMap;
import java.util.Map;

public class UIManager {
    private Map<String,UIScreen> screens;
    public static String activeScreen = "mainScreen";


    public UIManager(){
        screens = new HashMap<String,UIScreen>();
        screens.put("mainScreen",new MainMenu());
    }

    public void render(){
        // Rendering the active screen
        if(activeScreen!="") {
            screens.get(activeScreen).getStage().act(Gdx.graphics.getDeltaTime());
            screens.get(activeScreen).getStage().draw();
        }
    }
}
