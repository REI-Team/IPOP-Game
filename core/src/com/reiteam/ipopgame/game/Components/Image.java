package com.reiteam.ipopgame.game.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Image extends Actor {
    private Texture image;
    private float x, y;
    private float scaleX,scaleY;

    public Image(Texture image, float x, float y,float scaleX, float scaleY) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.scaleX=scaleX;
        this.scaleY=scaleY;
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

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(image, x, y,image.getWidth()*this.scaleX, image.getHeight()*this.scaleY);
    }
}
