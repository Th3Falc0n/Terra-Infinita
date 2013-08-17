package com.dafttech.terra.game.world.gen;

import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.gen.biomes.Biome;
import com.dafttech.terra.game.world.gen.biomes.BiomeGrassland;
import com.dafttech.terra.game.world.gen.calc.PerlinNoise;

public class WorldGenerator {
    public World world = null;
    public PerlinNoise noise;

    public WorldGenerator(World world) {
        this.world = world;
        noise = new PerlinNoise(TerraInfinita.rnd.nextInt(), 9, 0.48f);
    }

    public void generate() {
        getBiome(new Vector2(0, 0)).generateTerrain(this);
        getBiome(new Vector2(0, 0)).populate(this);
    }

    public Biome getBiome(Vector2 position) {
        return BiomeGrassland.instance;
    }

    public PerlinNoise getNoise() {
        return noise;
    }
}
