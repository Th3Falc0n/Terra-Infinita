package com.dafttech.terra.resources;

public class Resources {
    public static ImageLibrary TILES = new ImageLibrary();

    public static void init() {
        TILES.loadImage("error", "res/error.png");
        TILES.loadImage("dirt", "res/tiles/dirt.png");
        TILES.loadImage("mask_grass", "res/tiles/grass.png");
        TILES.loadImage("stone", "res/tiles/stone.png");
    }
}
