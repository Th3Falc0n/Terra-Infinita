package com.dafttech.wai.world;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;

import com.dafttech.wai.world.materials.TileDirt;
import com.dafttech.wai.world.renderers.RendererBlock;

public class Tile {
	static Map<String, Class> registry = new HashMap<String, Class>();
	
	public static void registerTile(String name, Class mat) {
		registry.put(name, mat);
	}
	
	public static Tile getInstanceOf(String name) {
		try {
			return (Tile)registry.get(name).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void init() {
		registerTile("dirt", TileDirt.class);
	}
	
	
	Position position;
	Image image;
	
	
	
	public Renderer getRenderer() {
		return RendererBlock.$Instance;
	}
	
	public Image getTexture() {
		return image;
	}
}
