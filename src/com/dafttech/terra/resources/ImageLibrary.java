package com.dafttech.terra.resources;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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

    public void loadRoundedImage(String name, String path) {
        loadImage(name, path);
        String filename = path.substring(0, path.lastIndexOf('.'));
        String fileending = path.substring(path.lastIndexOf('.'));
        loadImage(name + "_m", filename + "_m" + fileending);
        loadImage(name + "_tl", filename + "_tl" + fileending);
        loadImage(name + "_tr", filename + "_tr" + fileending);
        loadImage(name + "_bl", filename + "_bl" + fileending);
        loadImage(name + "_br", filename + "_br" + fileending);
    }

    public TextureRegion getImage(String name) {
        return library.containsKey(name) ? library.get(name) : library.get("error");
    }

    public TextureRegion getImage(String name, int num) {
        return library.containsKey(name + num) ? library.get(name + num) : library.get("error");
    }

    public TextureRegion getRoundedImage(String name) {
        return getImage(name + "_m");
    }

    public TextureRegion[] getRoundedImageEdges(String name) {
        return new TextureRegion[] { getImage(name + "_tl"), getImage(name + "_tr"), getImage(name + "_bl"), getImage(name + "_br") };
    }
}
