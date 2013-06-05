package com.dafttech.terra.world.gen.biomes;

import java.util.ArrayList;
import java.util.List;

import com.dafttech.terra.world.gen.WorldGenerator;

public abstract class Biome {
    public static List<Biome> biomes = new ArrayList<Biome>();

    String name;

    public Biome(String name) {
        this.name = name;
        biomes.add(this);
    }

    public abstract void generateTerrain(WorldGenerator gen);

    public abstract void populate(WorldGenerator gen);
}
