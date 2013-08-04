package com.dafttech.terra.resources;

import java.io.IOException;

import com.dafttech.terra.graphics.shaders.ShaderLibrary;

public class Resources {
    public static ImageLibrary TILES = new ImageLibrary();
    public static ImageLibrary ENTITIES = new ImageLibrary();
    public static ImageLibrary GUI = new ImageLibrary();

    public static void init() {
        TILES.loadImage("error", "res/error.png");
        TILES.loadImage("dirt", "res/tiles/dirt.png");
        TILES.loadImage("stone", "res/tiles/stone.png");
        TILES.loadImage("grass", "res/tiles/grass.png", 4);
        TILES.loadImage("torch", "res/tiles/torch.png");
        TILES.loadImage("weed", "res/tiles/weed.png");

        TILES.loadImage("mask_grass", "res/tiles/grass_mask.png");
        TILES.loadImage("bone", "res/tiles/bone.png");

        ENTITIES.loadImage("player", "res/entities/player.png");
        ENTITIES.loadImage("arrow", "res/entities/arrow.png");
        ENTITIES.loadImage("flame", "res/entities/flame.png");
        ENTITIES.loadImage("1px", "res/entities/1px.png");
        
        GUI.loadImage("slot", "res/ui/slot.png");
        GUI.loadImage("bar", "res/ui/bar.png");
        
        GUI.loadImage("button"      , "res/ui/button_empty.png");
        GUI.loadImage("button_hover", "res/ui/button_empty_hover.png");

        try {
            ShaderLibrary.loadShader("GaussH", "GaussH", "Gauss");
            ShaderLibrary.loadShader("GaussV", "GaussV", "Gauss");
            ShaderLibrary.loadShader("ShadowDistance", "Simple", "ShadowDistance");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
