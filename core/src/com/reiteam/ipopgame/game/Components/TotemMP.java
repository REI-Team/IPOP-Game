package com.reiteam.ipopgame.game.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.Align;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.reiteam.ipopgame.MainGame;
import com.reiteam.ipopgame.game.MultiplayerScreen;

public class TotemMP extends Actor {

    private Texture img;
    private Rectangle collider;
    private float x, y;
    private String id;
    private String ownerID;
    private BitmapFont font;
    private GlyphLayout glyphLayout;
    private String text;
    private float textWidth;
    private float marqueeTime = 0;
    private float maxWidth;
    private Music pop,error;

    public TotemMP(String id, String ownerID,float x, float y, String text, float maxWidth) {
        this.img = new Texture("totem.png");
        this.x = x;
        this.y = y;
        this.text = text;
        this.id=id;
        this.ownerID=ownerID;
        this.maxWidth = maxWidth;
        collider = new Rectangle(x, y, 50, 60);
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, text);
        textWidth = glyphLayout.width;
        pop = Gdx.audio.newMusic(Gdx.files.internal("music/pop.mp3"));
        error = Gdx.audio.newMusic(Gdx.files.internal("music/error.mp3"));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        marqueeTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        update();
        Vector2 screenPosition = worldToScreen(x, y, MultiplayerScreen.player);
        batch.draw(img, screenPosition.x, screenPosition.y, 50, 60);
        float marqueeOffset = marqueeTime * 50 % (textWidth + maxWidth);

        // Define el rectángulo de recorte (clipping) para el área donde se mostrará el texto.
        Rectangle scissor = new Rectangle(screenPosition.x, screenPosition.y + 80 - font.getCapHeight(), maxWidth, font.getCapHeight());

        // Termina el dibujo de la imagen antes de aplicar el recorte.
        batch.end();

        // Aplica el recorte al área de dibujo del batch.
        ScissorStack.pushScissors(scissor);

        // Comienza el dibujo del texto con el recorte aplicado.
        batch.begin();

        if (textWidth > maxWidth) {
            float offsetX = textWidth - marqueeOffset;
            if (offsetX > maxWidth) {
                offsetX = maxWidth;
            }
            float textX = screenPosition.x - offsetX;
            font.draw(batch, text, textX, screenPosition.y + 80, maxWidth, Align.left, false);
        } else {
            float textX = screenPosition.x - marqueeOffset;
            font.draw(batch, text, textX, screenPosition.y + 80);
        }

        // Termina el dibujo del texto con el recorte aplicado.
        batch.end();

        // Restaura el área de dibujo del batch a su estado original.
        ScissorStack.popScissors();

        // Comienza el dibujo nuevamente.
        batch.begin();
    }

    private void update(){
        collider.x=x;
        collider.y=y;
    }
    public boolean checkCollision(Rectangle guestCollider){
        if (guestCollider.overlaps(collider)) {
            // Los rectángulos están colisionando
            try {
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode rootNode = mapper.createObjectNode();
                rootNode.put("type", "removeTotem");
                rootNode.put("totemId", id);
                rootNode.put("id", ownerID);
                String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
                MultiplayerScreen.server.send(jsonString);
            }catch (Exception e){
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
    public Vector2 worldToScreen(float worldX, float worldY, Player player) {
        float screenX = worldX - player.getX() + MainGame.res[0] / 2;
        float screenY = worldY - player.getY() + MainGame.res[1] / 2;
        return new Vector2(screenX, screenY);
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }
}
