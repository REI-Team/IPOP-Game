package com.reiteam.ipopgame.game.Components;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Totem extends Actor {
    private BitmapFont font;
    private String text;
    private float speed;
    private float textWidth;
    private float position;
    private GlyphLayout glyphLayout;

    public Totem(BitmapFont font, String text, float speed) {
        this.font = font;
        this.text = text;
        this.speed = speed;
        this.glyphLayout = new GlyphLayout(font, text);
        this.textWidth = glyphLayout.width;
        this.position = getWidth();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        position -= speed * delta;
        if (position + textWidth < 0) {
            position = getWidth();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch, text, getX() + position, getY());
    }
}
