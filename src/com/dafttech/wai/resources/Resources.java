package com.dafttech.wai.resources;

public class Resources {
	public static ImageLibrary TILES = new ImageLibrary();
	
	public static void init() {
		TILES.loadImage("dirt", "res/tiles/dirt.png");
	}
}
