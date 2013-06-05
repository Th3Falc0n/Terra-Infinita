package com.dafttech.terra;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.dafttech.terra.event.Events;
import com.dafttech.terra.game.ScreenIngame;
import com.dafttech.terra.resources.Resources;
import com.dafttech.terra.world.Tile;

public class TerraInfinita extends Game implements ApplicationListener {
    FPSLogger fpsLogger;

    @Override
    public void create() {
        Gdx.app.log(Thread.currentThread().getName(), "Creating game...");

        Events.instance.init();
        Events.EVENT_INITPRE.callSync(this);

        fpsLogger = new FPSLogger();

        Resources.init();
        Tile.init();
        
        Gdx.graphics.setVSync(false);

        setScreen(new ScreenIngame());

        Events.EVENT_INITPOST.callSync(this);
    }

    @Override
    public void render() {
        super.render();

        fpsLogger.log();
    }

    @Override
    public void resize(int arg0, int arg1) {
        Events.EVENT_WINRESIZE.callSync(this);
    }

    @Override
    public void pause() {
        Events.EVENT_WINPAUSE.callSync(this);
    }

    @Override
    public void resume() {
        Events.EVENT_WINRESUME.callSync(this);
    }

    @Override
    public void dispose() {
        Events.EVENT_WINDISPOSE.callSync(this);
    }
}
