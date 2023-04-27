package com.reiteam.ipopgame.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MainMenu extends UIScreen{
    private Stage stage;

    public MainMenu(){
        stage = new Stage();
        Texture buttonTexture = new Texture("ui/Colored/grey.png");
        TextureRegion buttonRegion = new TextureRegion(buttonTexture);
        Skin skin = new Skin();
        skin.add("button", buttonRegion);
        Button button = new Button(skin.getDrawable("button"));
        button.setPosition(50, 50); // Establece la posición del botón
        button.setSize(100, 50); // Establece el tamaño del botón
        stage.addActor(button);
    }

    @Override
    public Stage getStage() {
        return stage;
    }
}
