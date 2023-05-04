package com.reiteam.ipopgame.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MarqueeText extends Actor {
    private BitmapFont font;
    private String text;
    private float speed;
    private float textWidth;
    private float position;

    public MarqueeText(BitmapFont font, String text, float speed) {
        this.font = font;
        this.text = text;
        this.speed = speed;
        //this.textWidth = font.getGlyphLayout().setText(font, text).width;
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
