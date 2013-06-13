package com.dafttech.terra.resources;

import java.io.IOException;

import com.dafttech.terra.graphics.shaders.ShaderLibrary;

public class Resources {
    public static ImageLibrary TILES = new ImageLibrary();
    public static ImageLibrary ENTITIES = new ImageLibrary();

    public static void init() {
        TILES.loadImage("error", "res/error.png");
        TILES.loadImage("dirt", "res/tiles/dirt.png");
        TILES.loadImage("stone", "res/tiles/stone.png");
        TILES.loadImage("grass", "res/tiles/grass.png", 4);

        TILES.loadImage("mask_grass", "res/tiles/grass_mask.png");
        TILES.loadImage("bone", "res/tiles/bone.png");
        
        ENTITIES.loadImage("player", "res/entities/player.png");
        
        try {
            ShaderLibrary.loadShader("GaussH", "GaussH", "Gauss");
            ShaderLibrary.loadShader("GaussV", "GaussV", "Gauss");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
