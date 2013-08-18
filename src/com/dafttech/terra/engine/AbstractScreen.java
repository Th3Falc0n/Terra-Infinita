package com.dafttech.terra.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

public abstract class AbstractScreen implements Screen {
    public Matrix4 projection = new Matrix4().setToOrtho(0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, 1);

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

        batch.setProjectionMatrix(projection);
        shr.setProjectionMatrix(projection);
    }

    @Override
    public void pause() {
    }

    public SpriteBatch batch = new SpriteBatch();
    public ShapeRenderer shr = new ShapeRenderer();
    public OrthographicCamera cam;

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

}
