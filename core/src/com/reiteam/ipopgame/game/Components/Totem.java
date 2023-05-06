package com.reiteam.ipopgame.game.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.reiteam.ipopgame.game.GameScreen;

public class Totem extends Actor {

    private Texture img;
    private Rectangle collider;
    private float x,y;
    public Totem(float x, float y) {
        this.img = new Texture("totem.png");
        this.x = x;
        this.y = y;
        collider = new Rectangle(x, y, 50, 60);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        update();
        batch.draw(img,GameScreen.img.getWidth()-x,GameScreen.img.getHeight()-y,50,60);
    }
    private void update(){
        collider.x=x;
        collider.y=y;
    }
    public void checkCollision(Rectangle guestCollider){
        if (guestCollider.overlaps(collider)) {
            // Los rectángulos están colisionando
            Gdx.app.log("Etiqueta", "Colision");
        }
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
