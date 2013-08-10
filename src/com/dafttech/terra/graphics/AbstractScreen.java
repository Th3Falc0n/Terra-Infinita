package com.dafttech.terra.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.dafttech.terra.TerraInfinita;

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
        Matrix4 normalProjection = new Matrix4();
        normalProjection.setToOrtho(0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, 1);

        batch.setProjectionMatrix(normalProjection);
    }

    @Override
    public void pause() {
    }

    public SpriteBatch batch = new SpriteBatch();
    public ShapeRenderer shr = new ShapeRenderer();
    public OrthographicCamera cam;

    public void render(float delta) {   
        
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

}
