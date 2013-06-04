package com.dafttech.wai;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.dafttech.wai.game.ScreenIngame;
import com.dafttech.wai.resources.Resources;
import com.dafttech.wai.world.Tile;

public class TerraInfinita extends Game implements ApplicationListener {
    FPSLogger fpsLogger;

    @Override
    public void create() {
        Gdx.app.log(Thread.currentThread().getName(), "Creating game...");

        fpsLogger = new FPSLogger();

        Resources.init();
        Tile.init();

        setScreen(new ScreenIngame());
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void render() {
        super.render();

        fpsLogger.log();
    }

    @Override
    public void resize(int arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

}
