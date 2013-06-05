package com.dafttech.terra.world.gen;

import com.dafttech.terra.world.Position;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.World;
import com.dafttech.terra.world.gen.biomes.Biome;
import com.dafttech.terra.world.gen.biomes.BiomeGrassland;
import com.dafttech.terra.world.subtiles.SubtileGrass;
import com.dafttech.terra.world.tiles.TileDirt;
import com.dafttech.terra.world.tiles.TileGrass;
import com.dafttech.terra.world.tiles.TileStone;

public class WorldGenerator {
    public World world = null;

    public WorldGenerator(World world) {
        this.world = world;
    }

    public void generate() {
        getBiome(new Vector2(0,0)).generateTerrain(this);
        getBiome(new Vector2(0,0)).populate(this);
    }

    public Biome getBiome(Vector2 position) {
        return BiomeGrassland.instance;
    }
}
