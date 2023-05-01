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
import com.badlogic.gdx.utils.Align;
import com.reiteam.ipopgame.MainGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChooseGrade extends UIScreen {
    private Stage stage;
    private Button play,multiplayer,chooseName,chooseGrade,chooseCharacter,ranking;
    private String[] grades_family = {"Informàtica","Administratiu","Automoció","Manteniment i serveis a la producció","Fabricació mecànica","Aigües"};
    private Map<String,String[]> grades;
    private Label.LabelStyle labelStyle;
    private Button next,back,uiBack;
    private Music button_click;
    private int currentPage = 1;
    private ArrayList<Button> buttons = new ArrayList<Button>();
    public ChooseGrade(){
        setupFontStyle();
        loadAllGrades();
        button_click = Gdx.audio.newMusic(Gdx.files.internal("music/button_click.mp3"));
        stage = new Stage(MainGame.viewport);
        //Setting a background image
        Texture backgroundImage = new Texture(Gdx.files.internal("ui/background.png"));
        Image background = new Image(backgroundImage);
        background.setSize(MainGame.res[0], MainGame.res[1]);
        stage.addActor(background);
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
        // Pagination buttons
        back = createButton("","ui/Colored/pag_back.png",(MainGame.res[0]/2)-100,MainGame.res[1]-650,32,42);
        back.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Aquí puedes agregar la acción que deseas realizar al tocar el botón
                //UIManager.activeScreen="";
                if(back.isDisabled()==false){
                    button_click.play();
                    clearButtons();
                    currentPage--;
                    loadFamilyGrade(currentPage);
                }
                return true;
            }
        });
        back.setDisabled(true);
        stage.addActor(back);
        next = createButton("","ui/Colored/pag_next.png",(MainGame.res[0]/2)-0,MainGame.res[1]-650,32,42);
        next.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Aquí puedes agregar la acción que deseas realizar al tocar el botón
                //UIManager.activeScreen="";
                if(next.isDisabled()==false){
                    button_click.play();
                    clearButtons();
                    currentPage++;
                    loadFamilyGrade(currentPage);
                }
                return true;
            }
        });
        next.setDisabled(true);
        loadFamilyGrade(currentPage);
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
        label.setPosition(0 , 500 ); // Center label
        label.setAlignment(Align.left);
        label.setWidth(button.getWidth());
        button.add(label);
        stage.addActor(button);
        return button;
    }
    public void clearButtons(){
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).remove();
        }
        buttons.clear();
    }
    private void loadFamilyGrade(int page){
        if(5*(page-1)<grades_family.length){
            next.setDisabled(false);
            back.setDisabled(false);
            for (int i = 0; i < 5; i++) {
                if(5*(page-1)+i<grades_family.length) {
                    String family = grades_family[5*(page-1)+i];
                    Button familyButton = createButton(family, "ui/Colored/buttonOrange-Medium.png", (MainGame.res[0] / 2) - 225, MainGame.res[1] - (100 + 70 * i), 450, 60);
                    familyButton.addListener(new InputListener() {
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            // Aquí puedes agregar la acción que deseas realizar al tocar el botón
                                button_click.play();
                                clearButtons();
                                loadGrade(family);
                            return true;
                        }
                    });
                    buttons.add(familyButton);
                }else {
                    next.setDisabled(true);
                }
                if(5*(page-1)==0) {
                    back.setDisabled(true);
                }
            }
        }
    }
    private void loadGrade(String family){
            next.setDisabled(true);
            back.setDisabled(true);
            Button gradeBack = createButton("","ui/Colored/back_blue.png",10,MainGame.res[1]-90,66,66);
            gradeBack.addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    // Aquí puedes agregar la acción que deseas realizar al tocar el botón
                    //UIManager.activeScreen="";
                    button_click.play();
                    gradeBack.remove();
                    clearButtons();
                    loadFamilyGrade(currentPage);
                    return true;
                }
            });
            for (int i = 0; i < grades.get(family).length; i++) {
                String grade = grades.get(family)[i];
                Button gradeButton = createButton(grade, "ui/Colored/buttonOrange-Large.png", (MainGame.res[0] / 2) - 278, MainGame.res[1] - (100 + 70 * i), 557, 60);
                gradeButton.addListener(new InputListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        // Aquí puedes agregar la acción que deseas realizar al tocar el botón
                        //UIManager.activeScreen="";
                        button_click.play();
                        gradeBack.remove();
                        clearButtons();
                        loadFamilyGrade(1);
                        MainGame.grade=grade;
                        MainMenu.disableButton("play",false);
                        UIManager.showScreen("mainScreen");
                        return true;
                    }
                });
                buttons.add(gradeButton);
            }
    }
    private void loadAllGrades(){
        grades = new HashMap<String, String[]>();
        grades.put("Informàtica", new String[]{"Sistemes microinformàtics i xarxes","Administració de sistemes informàtics en xarxa","Desenvolupament d’aplicacions multiplataforma","Desenvolupament d’aplicacions web"});
        grades.put("Administratiu",new String[]{"Gestió administrativa","Administració i finances","Assistència a la direcció"});
        grades.put("Automoció",new String[]{"Electromecànica de vehicles automòbils","Automoció"});
        grades.put("Manteniment i serveis a la producció",new String[]{"Manteniment electromecànics","Mecatrònica industrial"});
        grades.put("Fabricació mecànica",new String[]{"Mecanització","Programació de la producció en fabricació mecànica"});
        grades.put("Aigües",new String[]{"Gestió de l’aigua"});
    }
    @Override
    public Stage getStage() {
        return stage;
    }

}

