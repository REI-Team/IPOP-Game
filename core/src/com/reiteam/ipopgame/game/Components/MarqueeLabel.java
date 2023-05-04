package com.reiteam.ipopgame.game.Components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;


public class MarqueeLabel extends Label {

    // Constructor
    public MarqueeLabel(CharSequence text, LabelStyle style) {
        super(text, style);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Define los límites de la marquesina
        float marqueeWidth = 200; // El ancho que desees para la marquesina
        float marqueeHeight = 50; // El alto que desees para la marquesina

        // Limita el área de dibujo
        batch.flush();
        Rectangle scissors = new Rectangle();
        Rectangle clipBounds = new Rectangle(getX(), getY(), marqueeWidth, marqueeHeight);
        ScissorStack.calculateScissors(getStage().getCamera(), getStage().getViewport().getScreenX(), getStage().getViewport().getScreenY(),
                getStage().getViewport().getScreenWidth(), getStage().getViewport().getScreenHeight(), getStage().getBatch().getTransformMatrix(), clipBounds, scissors);
        ScissorStack.pushScissors(scissors);

        // Llama al método draw() de la clase base (Label) para dibujar el texto
        super.draw(batch, parentAlpha);

        // Restaura el área de dibujo
        batch.flush();
        ScissorStack.popScissors();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Actualiza la posición del texto
        moveBy(-1, 0); // Ajusta la velocidad de desplazamiento modificando el valor de -1

        // Si el texto ha desaparecido por completo de la marquesina, reinicia su posición
        if (getX() + getWidth() < 0) {
            setPosition(getParent().getWidth(), getY());
        }
    }
}
