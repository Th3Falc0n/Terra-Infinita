package com.dafttech.wai.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AbstractScreen implements Screen {

	public void dispose() {
	}

	public void hide() {
	}

	public void resize(int arg0, int arg1) {
	}

	public void resume() {
	}

	public void show() {
	}

	public void pause() {
	}

	public SpriteBatch batch = new SpriteBatch();

	@Override
	public void render(float arg0) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

}
