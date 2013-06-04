package com.dafttech.wai.resources;

public class Resources {
    public static ImageLibrary TILES = new ImageLibrary();

    public static void init() {
        TILES.loadImage("dirt", "res/tiles/dirt.png");
        TILES.loadImage("grass", "res/tiles/grass.png");
        TILES.loadImage("stone", "res/tiles/stone.png");
    }
}
