package com.dafttech.terra.game.world.gen;

import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.game.world.Chunk;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.gen.calc.PerlinNoise;

public class WorldGenerator {
    public World world = null;
    public PerlinNoise noise;

    public WorldGenerator(World world) {
        this.world = world;
        noise = new PerlinNoise(TerraInfinita.rnd.nextInt(), 9, 0.48f);
    }

    public void generateChunk(Chunk chunk) {
        chunk.getBiome().generateChunk(this, chunk);
        chunk.getBiome().populateChunk(this, chunk);
    }

    public PerlinNoise getNoise() {
        return noise;
    }
}
