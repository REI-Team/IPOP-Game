package com.reiteam.ipopgame.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.reiteam.ipopgame.MainGame;

public class MainMenu extends UIScreen{
    private Stage stage;
    private Button play,multiplayer,chooseName,chooseGrade,chooseCharacter,ranking;
    private Label.LabelStyle labelStyle;
    private Music musicaFondo;

    public MainMenu(){
        setupFontStyle();
        playMusic();
        stage = new Stage(MainGame.viewport);
        //Setting a background image
        Texture backgroundImage = new Texture(Gdx.files.internal("ui/background.png"));
        Image background = new Image(backgroundImage);
        background.setSize(MainGame.res[0], MainGame.res[1]);
        stage.addActor(background);
        //Setting logo
        Texture logotext = new Texture(Gdx.files.internal("ui/logo.png"));
        Image logo = new Image(logotext);
        logo.setPosition((MainGame.res[0]/2)-195,MainGame.res[1]-200);
        stage.addActor(logo);
        // Adding buttons
        play = createButton("Un jugador","ui/Colored/buttonBlue.png",(MainGame.res[0]/2)-125,MainGame.res[1]-300,250,60);
        play.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!play.isDisabled()){
                    musicaFondo.stop();
                    UIManager.showScreen("");
                    return true;
                }
                return true;
            }
        });
        disableButton(play,true);
        // Adding buttons without a actionListener to the stage
        multiplayer = createButton("Multijugador","ui/Colored/buttonBlue.png",(MainGame.res[0]/2)-125,MainGame.res[1]-370,250,60);
        chooseName = createButton("Triar nom","ui/Colored/buttonBlue.png",(MainGame.res[0]/2)-125,MainGame.res[1]-440,250,60);
        chooseGrade = createButton("Triar cicle","ui/Colored/buttonBlue.png",(MainGame.res[0]/2)-125,MainGame.res[1]-510,250,60);
        chooseGrade.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!chooseGrade.isDisabled()){
                    UIManager.showScreen("ChooseGrade");
                    return true;
                }
                return true;
            }
        });
        chooseCharacter = createButton("Triar personatge","ui/Colored/buttonBlue.png",(MainGame.res[0]/2)-125,MainGame.res[1]-580,250,60);

        ranking=createButton("Rànking","ui/Colored/buttonBlue.png",(MainGame.res[0]/2)-125,MainGame.res[1]-650,250,60);
        ranking.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                UIManager.showScreen("Ranking");
                return true;
            }
        });

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
        stage.addActor(button);
        return button;
    }
    public void disableButton(Button button, Boolean isDisabled){
        if(isDisabled){
            TextureRegion nuevaTextura = new TextureRegion(new Texture("ui/Colored/buttonDisabled.png"));
            Button.ButtonStyle nuevoEstilo = new Button.ButtonStyle();
            nuevoEstilo.up = new Image(nuevaTextura).getDrawable();
            Button.ButtonStyle estiloActual = button.getStyle();
            estiloActual.up = new Image(nuevaTextura).getDrawable();
            button.setStyle(estiloActual);
            button.setDisabled(true);
        }else {
            TextureRegion nuevaTextura = new TextureRegion(new Texture("ui/Colored/buttonBlue.png"));
            Button.ButtonStyle nuevoEstilo = new Button.ButtonStyle();
            nuevoEstilo.up = new Image(nuevaTextura).getDrawable();
            Button.ButtonStyle estiloActual = button.getStyle();
            estiloActual.up = new Image(nuevaTextura).getDrawable();
            button.setStyle(estiloActual);
            button.setDisabled(false);
        }
    }
    public void disableButton(String buttonName,Boolean isDisabled){
        switch (buttonName){
            case "play":
                disableButton(play,isDisabled);
            default:
                disableButton(play,isDisabled);
        }

    }
    private void playMusic(){
        musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("music/menu_background.mp3"));
        musicaFondo.setLooping(true);
        musicaFondo.play();
    }

    @Override
    public Stage getStage() {
        return stage;
    }
}
