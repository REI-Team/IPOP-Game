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
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.reiteam.ipopgame.MainGame;
import com.reiteam.ipopgame.utils.UtilsHTTP;


import java.util.ArrayList;
import java.util.Map;

public class Ranking extends UIScreen{
    private Stage stage;
    private Label.LabelStyle labelStyle;
    public int fetched;
    public int actualPage=1;
    private Button next,back,uiBack;
    private ArrayList<Actor> actors=new ArrayList<>();
    private Music button_click;

    public Ranking(){
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
        uiBack = createButton("","ui/Colored/back_red.png",10,MainGame.res[1]-90,66,66);
        uiBack.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Aquí puedes agregar la acción que deseas realizar al tocar el botón
                //UIManager.activeScreen="";
                if(uiBack.isDisabled()==false){
                    button_click.play();
                    UIManager.showScreen("mainScreen");
                }
                return true;
            }
        });
        stage.addActor(uiBack);
        back = createButton("","ui/Colored/pag_back.png",(MainGame.res[0]/2)-100,MainGame.res[1]-650,32,42);
        back.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Aquí puedes agregar la acción que deseas realizar al tocar el botón
                //UIManager.activeScreen="";
                if(back.isDisabled()==false){
                    button_click.play();
                    actualPage--;
                    fetchRows();
                    if(actualPage==1){
                        back.setDisabled(true);
                    }
                }
                return true;
            }
        });
        back.setDisabled(true);
        stage.addActor(back);
        next = createButton("","ui/Colored/pag_next.png",(MainGame.res[0]/2)+20,MainGame.res[1]-650,32,42);
        next.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Aquí puedes agregar la acción que deseas realizar al tocar el botón
                //UIManager.activeScreen="";
                if(next.isDisabled()==false){
                    button_click.play();
                    actualPage++;
                    fetchRows();
                    back.setDisabled(false);
                }
                return true;
            }
        });
        next.setDisabled(true);

        stage.addActor(next);
        //Button page = createButton("","ui/Colored/buttonGrey.png",(MainGame.res[0]/2)-50,MainGame.res[1]-650,50,60);
        // Adding buttons without a actionListener to the stage
        //stage.addActor(page);
        Actor lab=(createLabel(String.valueOf(actualPage),(MainGame.res[0]/2)-30,MainGame.res[1]-635));
        actors.add(lab);
        stage.addActor(lab);
        // POST
        fetchRows();
        // end POST

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

    private void fetchRows(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode rootNode = mapper.createObjectNode();
            rootNode.put("start", (actualPage-1)*10);
            rootNode.put("elements",11);
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
            UtilsHTTP.sendPOST(UtilsHTTP.protocol + "://" + UtilsHTTP.host + "/API/get_ranking", jsonString, (response) -> {
                //activity.runOnUiThread(()->{
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    Map<String, Object> reciv = objectMapper.readValue(response, Map.class);
                    if (reciv.get("status").equals("OK")) {
                        for (int i = 0; i < actors.size(); i++) {
                            actors.get(i).remove();
                        }
                        actors=new ArrayList<>();
                        Actor lab=(createLabel(String.valueOf(actualPage),(MainGame.res[0]/2)-30,MainGame.res[1]-635));
                        actors.add(lab);
                        stage.addActor(lab);

                        Gdx.app.debug("Etiqueta", reciv.get("result").getClass().toString());
                        ArrayList<Map<String,Object>> data = (ArrayList<Map<String, Object>>) reciv.get("result");
                        fetched=data.size();
                        if(fetched>=11){
                            next.setDisabled(false);
                        }else{
                            next.setDisabled(true);
                        }
                        for (int i = 0; (i < data.size()) && (i<10); i++) {
                            Map<String,Object> player = data.get(i);
                            Actor a=createLabel(player.get("name")+" : "+ player.get("score"),(MainGame.res[0]/2)-75,MainGame.res[1]-(180+(i*30)));
                            stage.addActor(a);
                            actors.add(a);
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (JsonProcessingException es) {
            es.printStackTrace();
        }
    }

    @Override
    public Stage getStage() {
        return stage;
    }
}
