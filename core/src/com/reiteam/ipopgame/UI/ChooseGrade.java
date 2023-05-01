package com.reiteam.ipopgame.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.reiteam.ipopgame.MainGame;

public class ChooseGrade extends UIScreen {
    private Stage stage;
    private Button play,multiplayer,chooseName,chooseGrade,chooseCharacter,ranking;
    private String[] grades = {"Grade 1","Grade 2","Grade 3","Grade 4","Grade 5"};
    private Label.LabelStyle labelStyle;
    public ChooseGrade(){
        setupFontStyle();
        stage = new Stage(MainGame.viewport);
        Gdx.input.setInputProcessor(stage);
        for (int i = 0; i < grades.length; i++) {
            createButton(grades[i],"ui/Colored/buttonBlue.png",(MainGame.res[0]/2)-125,MainGame.res[1]-(100+70*i),250,60);
        }
    }

    private void setupFontStyle(){
        BitmapFont font = new BitmapFont(); // Making a instance of BitmapFont for the font
        labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.font.getData().setScale(1.5f);
        labelStyle.fontColor = Color.BLACK;
    }
    private Button createButton(String text, String texturePath, int x, int y, int width, int height){
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
    @Override
    public Stage getStage() {
        return stage;
    }

}

