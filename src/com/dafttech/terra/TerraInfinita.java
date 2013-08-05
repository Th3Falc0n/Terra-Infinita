package com.dafttech.terra;

import java.util.Random;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Texture;
import com.dafttech.terra.event.Events;
import com.dafttech.terra.game.InputHandler;
import com.dafttech.terra.game.ScreenIngame;
import com.dafttech.terra.graphics.gui.Tooltip;
import com.dafttech.terra.resources.Resources;
import com.dafttech.terra.world.tiles.Tile;

public class TerraInfinita extends Game implements ApplicationListener {
    FPSLogger fpsLogger;

    public static Random rnd = new Random();
    public static ScreenIngame screenIngame;

    @Override
    public void create() {
        Gdx.app.log(Thread.currentThread().getName(), "Creating game...");

        Texture.setEnforcePotImages(false);

        Events.init();
        Events.EVENT_INITPRE.callSync(this);

        fpsLogger = new FPSLogger();

        Resources.init();
        Tile.init();
        InputHandler.init();
        Tooltip.init();

        DisplayMode desktop = Gdx.graphics.getDesktopDisplayMode();
        
        Gdx.graphics.setDisplayMode(desktop);

        screenIngame = new ScreenIngame();
        
        setScreen(screenIngame);

        Events.EVENT_INITPOST.callSync(this);
    }

    @Override
    public void render() {
        InputHandler.update();
        
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
