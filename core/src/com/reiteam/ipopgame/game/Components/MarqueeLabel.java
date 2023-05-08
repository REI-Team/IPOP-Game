package com.reiteam.ipopgame.game.Components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

public class MarqueeLabel extends Label {

    private float scrollSpeed;
    private float elapsedTime;
    private float textWidth;
    private float wrapWidth;

    public MarqueeLabel(CharSequence text, LabelStyle style, float wrapWidth, float scrollSpeed, float width) {
        super(text, style);
        this.scrollSpeed = scrollSpeed;
        this.wrapWidth = wrapWidth;
        this.textWidth = getGlyphLayout().width;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Drawable background = getStyle().background;

        batch.flush();
        float originalX = getX();
        float originalY = getY();

        // Crea un área de recorte para mostrar solo el texto dentro del área de la etiqueta
        Rectangle scissors = new Rectangle();
        Rectangle clipBounds = new Rectangle(getX(), getY(), wrapWidth, getHeight());
        ScissorStack.calculateScissors(getStage().getCamera(), batch.getTransformMatrix(), clipBounds, scissors);
        ScissorStack.pushScissors(scissors);

        // Mueve el texto de acuerdo al tiempo transcurrido
        elapsedTime += scrollSpeed;
        if (elapsedTime > textWidth) {
            elapsedTime = 0;
        }

        setPosition(originalX - elapsedTime, originalY);
        super.draw(batch, parentAlpha);

        // Dibuja el texto nuevamente al final si es necesario
        if (elapsedTime > 0 && elapsedTime < wrapWidth) {
            setPosition(originalX + textWidth - elapsedTime, originalY);
            super.draw(batch, parentAlpha);
        }

        // Restaura la posición original y el fondo
        setPosition(originalX, originalY);

        batch.flush();
        ScissorStack.popScissors();
    }
}
