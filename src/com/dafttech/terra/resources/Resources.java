package com.dafttech.terra.resources;

public class Resources {
    public static ImageLibrary TILES = new ImageLibrary();

    public static void init() {
        TILES.loadImage("error", "res/error.png");
        TILES.loadImage("dirt", "res/tiles/dirt.png");
        TILES.loadImage("stone", "res/tiles/stone.png");
        
        for (int i = 0; i <= 4; i++) {
            TILES.loadImage("grass" + i, "res/tiles/grass_" + i + ".png");
        }
        
        TILES.loadImage("mask_grass", "res/tiles/grass_mask.png");
        TILES.loadImage("bone", "res/tiles/bone.png");
    }
}
