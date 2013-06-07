package com.dafttech.terra.graphics;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ImageLibrary {
    public Map<String, TextureRegion> library = new HashMap<String, TextureRegion>();

    public void loadImage(String name, String path) {
        library.put(name, new TextureRegion(new Texture(path)));
    }

    public void loadImage(String name, String path, int num) {
        for (int i = 0; i <= num; i++) {
            library.put(name + i,
                    new TextureRegion(new Texture(path.substring(0, path.lastIndexOf(".")) + "_" + i + path.substring(path.lastIndexOf(".")))));
        }
    }

    public TextureRegion getImage(String name) {
        return library.containsKey(name) ? library.get(name) : library.get("error");
    }

    public TextureRegion getImage(String name, int num) {
        return library.containsKey(name + num) ? library.get(name + num) : library.get("error");
    }
}
