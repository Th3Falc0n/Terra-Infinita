package com.dafttech.terra.resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class ImageLibrary {
    public Map<String, TextureRegion> library = new HashMap<String, TextureRegion>();

    public void loadImage(String name, String path) {
        library.put(name, new TextureRegion(new Texture(path)));
        library.get(name).flip(false, true);
    }

    public void loadImage(String name, String path, int num) {
        for (int i = 0; i <= num; i++) {
            library.put(name + i,
                    new TextureRegion(new Texture(path.substring(0, path.lastIndexOf(".")) + "_" + i + path.substring(path.lastIndexOf(".")))));
            library.get(name + i).flip(false, true);
        }
    }

    public TextureRegion getImage(String name) {
        return library.containsKey(name) ? library.get(name) : library.get("error");
    }

    public TextureRegion getImage(String name, int num) {
        return library.containsKey(name + num) ? library.get(name + num) : library.get("error");
    }
}
