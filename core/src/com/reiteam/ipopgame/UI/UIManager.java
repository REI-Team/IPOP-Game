package com.reiteam.ipopgame.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.reiteam.ipopgame.MainGame;
import com.reiteam.ipopgame.game.GameScreen;

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
        screens.put("finishGame",new FinishGame());
        //screens.put("finishGameMultiplayer", new FinishGameMultiplayer());
        showScreen("mainScreen");
        //finishGameMultiplayer
    }

    public void render(){
        // Rendering the active screen
        //System.out.println(UIManager.activeScreen);
        if(activeScreen!="" & activeScreen!="mpScreen") {
            screens.get(activeScreen).getStage().act(Gdx.graphics.getDeltaTime());
            screens.get(activeScreen).getStage().draw();
        }
    }
    public static void showScreen(String screenName){

        if(screenName.equals("gameScreen")){
            MainGame.gameScreen.generateTotems();
            activeScreen="";
            Gdx.input.setInputProcessor(null);
        }else if(screenName.equals("mpScreen")){
            activeScreen="mpScreen";
            Gdx.input.setInputProcessor(null);
        }else if(screenName.equals("finishGame")){
            activeScreen=screenName;
            Gdx.input.setInputProcessor(screens.get(screenName).getStage());
            FinishGame.getInstance().updateStats();
        }else if(screenName.equals("finishGameMultiplayer")){
            activeScreen=screenName;
            Gdx.input.setInputProcessor(screens.get(screenName).getStage());
            FinishGameMultiplayer.getInstance().updateStats();
            //Passem a pantalla final i ens ofereix guardar el rècord al servidor. La desconnexió implica la finalització de la partida.
        }else if(!screenName.equals("")){
            activeScreen=screenName;
            Gdx.input.setInputProcessor(screens.get(screenName).getStage());
        }else{
            Gdx.input.setInputProcessor(null);
        }

    }
    public Stage getCurrentStage(){
        return screens.get(activeScreen).getStage();
    }
}
