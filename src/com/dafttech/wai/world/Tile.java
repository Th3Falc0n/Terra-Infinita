package com.dafttech.wai.world;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.wai.world.materials.TileDirt;
import com.dafttech.wai.world.renderers.RendererBlock;

@SuppressWarnings("rawtypes")
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
	TextureRegion image;
	
	public Tile(Position pos, TextureRegion textureRegion) {
		position = pos;
		image = textureRegion;
	}
	
	public Renderer getRenderer() {
		return RendererBlock.$Instance;
	}
	
	public TextureRegion getImage() {
		return image;
	}
	
	public Position getPosition() {
		return position;
	}
}
