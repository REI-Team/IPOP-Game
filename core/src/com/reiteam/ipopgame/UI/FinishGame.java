package com.reiteam.ipopgame.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.reiteam.ipopgame.MainGame;
import com.reiteam.ipopgame.utils.UtilsHTTP;

import java.util.ArrayList;
import java.util.Map;

public class FinishGame extends UIScreen{
    private Stage stage;
    private Label.LabelStyle labelStyle;
    public int fetched;
    public int actualPage=1;
    private Button next,back,uiBack;
    private ArrayList<Actor> actors=new ArrayList<>();
    private Music button_click;
    private Button setRecord, returnMain;
    private Label success,error,time;
    private static FinishGame instance;
    public static int port = 3000;
    public static String protocol = "https";
    public static String host = "nodejs-api-production-a765.up.railway.app";

    public FinishGame(){
        instance=this;
        setupFontStyle();
        button_click = Gdx.audio.newMusic(Gdx.files.internal("music/button_click.mp3"));
        stage = new Stage(MainGame.viewport);
        //Setting a background image
        Texture backgroundImage = new Texture(Gdx.files.internal("ui/background.png"));
        Image background = new Image(backgroundImage);
        background.setSize(MainGame.res[0], MainGame.res[1]);
        stage.addActor(background);
        //Setting Menu UI
        Texture logotext = new Texture(Gdx.files.internal("ui/uiMenu_Ranking.png"));
        Image logo = new Image(logotext);
        logo.setSize(650,500);
        logo.setPosition(320,200);
        stage.addActor(logo);
        // Adding buttons
        returnMain = createButton("Set Record","ui/Colored/buttonBlue.png",(MainGame.res[0]/2)+50,MainGame.res[1]-600,250,60);
        returnMain.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!setRecord.isDisabled()){
                    button_click.play();
                    setRecord();
                    MainGame.success=0;
                    MainGame.error=0;
                    MainGame.time=0;
                    UIManager.showScreen("mainScreen");
                    return true;
                }
                return true;
            }
        });
        stage.addActor(returnMain);
        setRecord = createButton("Main Menu","ui/Colored/buttonBlue.png",(MainGame.res[0]/2)-300,MainGame.res[1]-600,250,60);
        setRecord.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!setRecord.isDisabled()){
                    button_click.play();
                    MainGame.success=0;
                    MainGame.error=0;
                    MainGame.time=0;
                    UIManager.showScreen("mainScreen");
                    return true;
                }
                return true;
            }
        });
        stage.addActor(setRecord);
        success=createLabel("Success Totems:",350,300);
        error=createLabel("Error Totems:",700,300);
        time=createLabel("Time:",350,250);
        stage.addActor(success);
        stage.addActor(error);
        stage.addActor(time);
        // end POST

    }
    public void updateStats(){
        this.success.setText("Success Totems:"+String.valueOf(MainGame.success));
        this.error.setText("Error Totems:"+String.valueOf(MainGame.error));
        this.time.setText("Time:"+String.valueOf(MainGame.time));
    }
    private void setupFontStyle(){
        BitmapFont font = new BitmapFont(); // Making a instance of BitmapFont for the font
        labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.font.getData().setScale(1.5f);
        labelStyle.fontColor = Color.BLACK;
    }

    private Button createButton(String text,String texturePath,int x,int y, int width,int height){
        Skin skin = new Skin();
        //Setting button texture
        Texture buttonTexture = new Texture(texturePath);
        TextureRegion buttonRegion = new TextureRegion(buttonTexture);
        skin.add("button", buttonRegion);
        skin.add("default", labelStyle);
        Button button = new Button(skin.getDrawable("button"));
        button.setPosition(x, y); // Setting button pos
        button.setSize(width, height); // Setting button size
        Label label = new Label(text, skin); // Making a label instance
        label.setPosition(button.getX() + button.getWidth()/2, button.getY() + button.getHeight()/2); // Center label
        label.setAlignment(Align.center);
        button.add(label);
        return button;
    }

    private Label createLabel(String text,int x,int y){
        Skin skin = new Skin();
        skin.add("default", labelStyle);
        Label label = new Label(text, skin); // Making a label instance
        label.setPosition(x, y); // Center label
        label.setAlignment(Align.center);
        return label;
    }

    private void setRecord(){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("name", "Prueba");
        rootNode.put("degree", "2");
        rootNode.put("success",String.valueOf(MainGame.success));
        rootNode.put("errors",String.valueOf(MainGame.error));
        rootNode.put("time",String.valueOf(MainGame.time));
        try {
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
            UtilsHTTP.sendPOST(UtilsHTTP.protocol + "://" + UtilsHTTP.host + "/API/set_record", jsonString, (response) -> {
                ObjectMapper objectMapper = new ObjectMapper();
                try{
                    Map<String, Object> reciv = objectMapper.readValue(response, Map.class);
                    if(reciv.get("status").equals("OK")){
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public static FinishGame getInstance(){
        return instance;
    }
    @Override
    public Stage getStage() {
        return stage;
    }
}
