package com.dafttech.terra.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class AbstractScreen implements Screen {

    @Override
    public void dispose() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void resize(int arg0, int arg1) {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {
    }

    @Override
    public void pause() {
    }

    public SpriteBatch batch = new SpriteBatch();
    public ShapeRenderer shr = new ShapeRenderer();

    @Override
    public void render(float arg0) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

}
