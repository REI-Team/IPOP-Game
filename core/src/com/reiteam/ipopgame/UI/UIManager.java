package com.reiteam.ipopgame.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

import org.graalvm.compiler.replacements.Log;

import java.util.HashMap;
import java.util.Map;

public class UIManager {
    private Map<String,UIScreen> screens;
    public static String activeScreen = "Ranking";


    public UIManager(){
        screens = new HashMap<String,UIScreen>();
        screens.put("mainScreen",new MainMenu());
        screens.put("Ranking",new Ranking());
    }

    public void render(){
        // Rendering the active screen
        //System.out.println(UIManager.activeScreen);
        if(activeScreen!="") {
            screens.get(activeScreen).getStage().act(Gdx.graphics.getDeltaTime());
            screens.get(activeScreen).getStage().draw();
        }
    }
}
