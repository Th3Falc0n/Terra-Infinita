package com.dafttech.wai.resources;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ImageLibrary {
	public Map<String, TextureRegion> library = new HashMap<String, TextureRegion>();
	
	public void loadImage(String name, String path) {
		library.put(name, new TextureRegion(new Texture(path)));
	}
	
	public TextureRegion getImage(String name) {
		return library.get(name);
	}
}
