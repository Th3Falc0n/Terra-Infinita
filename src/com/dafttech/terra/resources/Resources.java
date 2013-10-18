package com.dafttech.terra.resources;

import java.io.IOException;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.dafttech.terra.engine.shaders.ShaderLibrary;

public class Resources {
    public static ImageLibrary TILES = new ImageLibrary();
    public static ImageLibrary ENTITIES = new ImageLibrary();
    public static ImageLibrary ITEMS = new ImageLibrary();
    public static ImageLibrary GUI = new ImageLibrary();
    public static ImageLibrary LIGHT = new ImageLibrary();

    public static BitmapFont GUI_FONT;

    public static void init() {
        TILES.loadImage("error", "res/error.png");
        TILES.loadImage("air", "res/tiles/air.png");
        TILES.loadImage("dirt", "res/tiles/dirt.png");
        TILES.loadImage("stone", "res/tiles/stone.png");
        TILES.loadImage("grass", "res/tiles/grass.png", 4);
        TILES.loadImage("torch", "res/tiles/torch.png");
        TILES.loadImage("weed", "res/tiles/weed.png");
        TILES.loadImage("sand", "res/tiles/sand.png");
        TILES.loadImage("fire", "res/tiles/fire.png");
        TILES.loadImage("fence", "res/tiles/fence.png");
        TILES.loadImage("fence_cracks", "res/tiles/fence_cracks.png");
        TILES.loadImage("fence_lianas", "res/tiles/fence_lianas.png");
        TILES.loadImage("fence_lianas_flowers", "res/tiles/fence_lianas_flowers.png");

        TILES.loadImage("mask_grass", "res/tiles/grass_mask.png");
        TILES.loadImage("mask_grass_dry", "res/tiles/grass_mask_dry.png");
        TILES.loadImage("bone", "res/tiles/bone.png");

        ENTITIES.loadImage("player", "res/entities/player.png");
        ENTITIES.loadImage("arrow", "res/entities/arrow.png");
        ENTITIES.loadImage("flame", "res/entities/flame.png");
        ENTITIES.loadImage("1px", "res/entities/1px.png");
        ENTITIES.loadImage("dynamite", "res/entities/dynamite.png");
        ENTITIES.loadImage("explosion", "res/entities/explosion.png");
        ENTITIES.loadImage("glowstick", "res/entities/glowstick.png");
        ENTITIES.loadImage("rainbow", "res/entities/rainbow.png");

        ITEMS.loadImage("rainbowgun", "res/items/rainbowgun.png");

        GUI.loadImage("slot", "res/ui/slot.png");
        GUI.loadImage("bar", "res/ui/bar.png");
        GUI.loadImage("bar_mask", "res/ui/bar_mask.png");
        GUI.loadImage("button", "res/ui/button_empty.png");

        LIGHT.loadImage("pointlight", "res/lighting/pointlight.png");

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(new FileHandle("res/ui/button_font.ttf"));

        GUI_FONT = gen.generateFont(12, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!\"§$%&/()=?[]{}\\ßöüä'*#+~-.,;:_<>|^°", true);

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
