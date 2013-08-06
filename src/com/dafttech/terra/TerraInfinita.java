package com.dafttech.terra;

import java.util.Random;

import org.lwjgl.opengl.Display;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Texture;
import com.dafttech.eventmanager.Event;
import com.dafttech.eventmanager.EventListener;
import com.dafttech.terra.game.Events;
import com.dafttech.terra.game.InputHandler;
import com.dafttech.terra.game.ScreenIngame;
import com.dafttech.terra.game.ScreenPauseMenu;
import com.dafttech.terra.game.world.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.tiles.Tile;
import com.dafttech.terra.graphics.gui.Tooltip;
import com.dafttech.terra.graphics.gui.containers.ContainerOnscreen;
import com.dafttech.terra.resources.Resources;

public class TerraInfinita extends Game implements ApplicationListener {
    public static TerraInfinita $ = new TerraInfinita();
    
    FPSLogger fpsLogger;

    public static Random rnd = new Random();
    public ScreenIngame screenIngame;
    public ScreenPauseMenu screenPause;
    
    World world;
    
    boolean wasFocused = false;

    public boolean isFocused() {
        return Display.isActive();
    }

    @Override
    public void create() {
        Gdx.app.log(Thread.currentThread().getName(), "Creating game...");

        Texture.setEnforcePotImages(false);

        Events.init();
        Events.EVENT_INITPRE.callSync(this);

        fpsLogger = new FPSLogger();

        Resources.init();
        InputHandler.init();
        Tooltip.init();

        world = new World(new Vector2(1000, 500));
        
        screenIngame = new ScreenIngame(world);
        screenPause = new ScreenPauseMenu(world);

        setScreen(screenPause);

        Events.EVENT_INITPOST.callSync(this);
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
