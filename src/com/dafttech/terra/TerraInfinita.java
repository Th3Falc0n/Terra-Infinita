package com.dafttech.terra;

import java.util.Random;

import org.lwjgl.opengl.Display;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Texture;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.MouseSlot;
import com.dafttech.terra.engine.gui.Tooltip;
import com.dafttech.terra.engine.input.InputHandler;
import com.dafttech.terra.game.Events;
import com.dafttech.terra.game.ScreenIngame;
import com.dafttech.terra.game.ScreenPauseMenu;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.resources.Resources;

public class TerraInfinita extends Game implements ApplicationListener {
    public static TerraInfinita $ = new TerraInfinita();

    FPSLogger fpsLogger;

    public static Random rnd = new Random();
    public ScreenIngame screenIngame;
    public ScreenPauseMenu screenPause;

    public World world;

    boolean wasFocused = false;

    public boolean isFocused() {
        return Display.isActive();
    }

    @Override
    public void create() {

        Gdx.app.log(Thread.currentThread().getName(), "Creating game...");

        Texture.setEnforcePotImages(false);

        Events.init();
        Events.EVENTMANAGER.callSync(Events.EVENT_INITPRE, this);

        fpsLogger = new FPSLogger();

        Resources.init();
        InputHandler.init();
        Tooltip.init();
        MouseSlot.init();

        world = new World(new Vector2(2000, 1000));

        screenIngame = new ScreenIngame(world);
        screenPause = new ScreenPauseMenu(world);

        setScreen(screenPause);

        Events.EVENTMANAGER.callSync(Events.EVENT_INITPOST, this);
    }

    @Override
    public void render() {
        if (!isFocused() && wasFocused) {
            setScreen(screenPause);
            wasFocused = false;
        }

        if (isFocused() && !wasFocused) {
            wasFocused = true;
        }

        super.render();

        fpsLogger.log();
    }

    @Override
    public void resize(int arg0, int arg1) {
        Events.EVENTMANAGER.callSync(Events.EVENT_WINRESIZE, this);
    }

    @Override
    public void pause() {
        Events.EVENTMANAGER.callSync(Events.EVENT_WINPAUSE, this);
    }

    @Override
    public void resume() {
        Events.EVENTMANAGER.callSync(Events.EVENT_WINRESUME, this);
    }

    @Override
    public void dispose() {
        Events.EVENTMANAGER.callSync(Events.EVENT_WINDISPOSE, this);
    }
}
