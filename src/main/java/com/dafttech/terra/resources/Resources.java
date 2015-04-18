package com.dafttech.terra.resources;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.dafttech.terra.engine.shaders.ShaderLibrary;

public class Resources {
    public static ImageLibrary TILES = new ImageLibrary();
    public static ImageLibrary ENTITIES = new ImageLibrary();
    public static ImageLibrary ITEMS = new ImageLibrary();
    public static ImageLibrary SKILLS = new ImageLibrary();
    public static ImageLibrary GUI = new ImageLibrary();
    public static ImageLibrary LIGHT = new ImageLibrary();

    public static BitmapFont GUI_FONT;

    public static void init() {
        TILES.loadImage("error", "error.png");
        TILES.loadImage("air", "tiles/air.png");
        TILES.loadImage("dirt", "tiles/dirt.png");
        TILES.loadImage("stone", "tiles/stone.png");
        TILES.loadImage("grass", "tiles/grass.png", 4);
        TILES.loadImage("torch", "tiles/torch.png");
        TILES.loadImage("weed", "tiles/weed.png");
        TILES.loadImage("sand", "tiles/sand.png");
        TILES.loadImage("fire", "tiles/fire.png");
        TILES.loadImage("fence", "tiles/fence.png");
        TILES.loadImage("fence_cracks", "tiles/fence_cracks.png");
        TILES.loadImage("fence_lianas", "tiles/fence_lianas.png");
        TILES.loadImage("fence_lianas_flowers", "tiles/fence_lianas_flowers.png");
        TILES.loadImage("log", "tiles/log.png");
        TILES.loadImage("leaf", "tiles/leaf.png");
        TILES.loadImage("sapling", "tiles/sapling_1.png");

        TILES.loadImage("mask_grass", "tiles/grass_mask.png");
        TILES.loadImage("mask_grass_dry", "tiles/grass_mask_dry.png");
        TILES.loadImage("bone", "tiles/bone.png");
        TILES.loadImage("water", "tiles/water.png");
        TILES.loadImage("wateranim", "tiles/water.png", 3);
        TILES.loadImage("glowgoo", "tiles/glowgoo.png", 3);

        ENTITIES.loadImage("player", "entities/player.png");
        ENTITIES.loadImage("arrow", "entities/arrow.png");
        ENTITIES.loadImage("flame", "entities/flame.png");
        ENTITIES.loadImage("1px", "entities/1px.png");
        ENTITIES.loadImage("dynamite", "entities/dynamite.png");
        ENTITIES.loadImage("explosion", "entities/explosion.png");
        ENTITIES.loadImage("glowstick", "entities/glowstick.png");
        ENTITIES.loadImage("rainbow", "entities/rainbow.png");
        ENTITIES.loadImage("beamDig", "entities/beamDig.png");
        ENTITIES.loadImage("rain", "entities/rain.png", 3);

        ENTITIES.loadImage("raindrop", "entities/raindrop.png");
        ENTITIES.loadImage("splash", "entities/splash.png");

        ITEMS.loadImage("rainbowgun", "items/rainbowgun.png");
        ITEMS.loadImage("digStaff", "items/digStaff.png");

        ITEMS.loadImage("sword_single", "items/sword_single.png");

        SKILLS.loadImage("aa_single", "skills/aa_single.png");
        SKILLS.loadImage("healing_strike", "skills/healing_strike.png");
        SKILLS.loadImage("smashing_strike", "skills/smashing_strike.png");
        SKILLS.loadImage("stab", "skills/stab.png");

        GUI.loadImage("slot", "ui/slot.png");
        GUI.loadImage("bar", "ui/bar.png");
        GUI.loadImage("bar_mask", "ui/bar_mask.png");
        GUI.loadImage("button", "ui/button_empty.png");

        LIGHT.loadImage("pointlight", "lighting/pointlight.png");

        FileHandle handle = null;
        try {
            Constructor<FileHandle> contructor = FileHandle.class.getDeclaredConstructor(String.class, Files.FileType.class);
            contructor.setAccessible(true);
            handle = contructor.newInstance("ui/button_font.ttf", Files.FileType.Classpath);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(handle);

        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!\"§$%&/()=?[]{}\\'*#+~-.,;:_<>^";
        param.size = 12;

        GUI_FONT = gen.generateFont(param);

        gen.dispose();

        try {
            ShaderLibrary.loadShader("GaussH", "GaussH", "Gauss");
            ShaderLibrary.loadShader("GaussV", "GaussV", "Gauss");
            ShaderLibrary.loadShader("ShadowDistance", "Simple", "ShadowDistance");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
