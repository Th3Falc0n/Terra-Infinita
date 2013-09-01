package com.dafttech.terra.game.world.gen.biomes;

import java.util.ArrayList;
import java.util.List;

import com.dafttech.terra.game.world.Chunk;
import com.dafttech.terra.game.world.gen.WorldGenerator;

public abstract class Biome {
    public static List<Biome> biomes = new ArrayList<Biome>();

    String name;

    public Biome(String name) {
        this.name = name;
        biomes.add(this);
    }

    public abstract void generateChunk(WorldGenerator gen, Chunk chunk);
    public abstract void populateChunk(WorldGenerator gen, Chunk chunk);
}
