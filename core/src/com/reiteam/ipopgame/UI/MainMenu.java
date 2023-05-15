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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.reiteam.ipopgame.MainGame;
import com.reiteam.ipopgame.game.GameScreen;
import com.reiteam.ipopgame.game.MultiplayerScreen;

public class MainMenu extends UIScreen{
    private Stage stage;
    private static Button play,multiplayer,chooseName,chooseGrade,chooseCharacter,ranking;
    private Label.LabelStyle labelStyle;
    private Music backgroundMusic, button_click;
    Texture textfieldBackgroundTexture = new Texture(Gdx.files.internal("ui/textField.png"));
    TextureRegionDrawable textfieldBackgroundDrawable = new TextureRegionDrawable(textfieldBackgroundTexture);

    public MainMenu(){
        setupFontStyle();
        // Setting up music and sound effects
        playMusic();
        button_click = Gdx.audio.newMusic(Gdx.files.internal("music/button_click.mp3"));
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
                    button_click.play();
                    backgroundMusic.stop();
                    MainGame.gameStarted=true;
                    GameScreen.toggleMusic(true);
                    UIManager.showScreen("gameScreen");
                    return true;
                }
                return true;
            }
        });
        disableButton(play,true);
        // Adding buttons without a actionListener to the stage
        multiplayer = createButton("Multijugador","ui/Colored/buttonBlue.png",(MainGame.res[0]/2)-125,MainGame.res[1]-370,250,60);
        multiplayer.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!chooseGrade.isDisabled()){
                    button_click.play();
                    backgroundMusic.stop();
                    MainGame.gameStarted=true;
                    UIManager.showScreen("mpScreen");
                    MultiplayerScreen.getInstance().startConnection();
                    MultiplayerScreen.toggleMusic(true);
                    return true;
                }
                return true;
            }
        });
        disableButton(multiplayer,false);

        chooseGrade = createButton("Triar cicle","ui/Colored/buttonBlue.png",(MainGame.res[0]/2)-125,MainGame.res[1]-440,250,60);
        chooseGrade.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!chooseGrade.isDisabled()){
                    button_click.play();
                    UIManager.showScreen("ChooseGrade");
                    return true;
                }
                return true;
            }
        });
        chooseCharacter = createButton("Triar personatge","ui/Colored/buttonBlue.png",(MainGame.res[0]/2)-125,MainGame.res[1]-510,250,60);
        disableButton(chooseCharacter,true);
        ranking=createButton("Rànking","ui/Colored/buttonBlue.png",(MainGame.res[0]/2)-125,MainGame.res[1]-580,250,60);
        ranking.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                button_click.play();
                UIManager.showScreen("Ranking");
                return true;
            }
        });

        // Change username
        Skin skin = new Skin();
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();

        textFieldStyle.background = textfieldBackgroundDrawable;
        BitmapFont defaultFont = new BitmapFont();
        skin.add("default-font", defaultFont, BitmapFont.class);


        // Define el estilo de la fuente (puedes cargar una fuente personalizada si lo deseas)
        textFieldStyle.font = skin.getFont("default-font");

        // Define el color del texto
        textFieldStyle.fontColor = Color.BLACK;
        textFieldStyle.background.setLeftWidth(textFieldStyle.background.getLeftWidth() + 10);

        // Añade el estilo al skin
        skin.add("default", textFieldStyle);
        TextField textField = new TextField("Player", skin);

        // Posiciona y ajusta el tamaño del TextField según sea necesario
        textField.setPosition(10, 10);
        textField.setSize(200, 50);
        stage.addActor(textField);
        //Button
        chooseName = createButton("Aceptar","ui/Colored/buttonGreen.png",150,10,100,50);
        chooseName.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MainGame.username=textField.getText();
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
    public static void disableButton(Button button, Boolean isDisabled){
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
    public static void disableButton(String buttonName,Boolean isDisabled){
        switch (buttonName){
            case "play":
                disableButton(play,isDisabled);
            default:
                disableButton(play,isDisabled);
        }

    }
    private void playMusic(){
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/menu_background.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }

    @Override
    public Stage getStage() {
        return stage;
    }
}
