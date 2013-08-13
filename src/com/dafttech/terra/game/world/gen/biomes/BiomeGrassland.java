package com.dafttech.terra.game.world.gen.biomes;

import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.game.world.Position;
import com.dafttech.terra.game.world.gen.WorldGenerator;
import com.dafttech.terra.game.world.gen.calc.PerlinNoise;
import com.dafttech.terra.game.world.subtiles.SubtileGrass;
import com.dafttech.terra.game.world.tiles.TileDirt;
import com.dafttech.terra.game.world.tiles.TileGrass;
import com.dafttech.terra.game.world.tiles.TileStone;
import com.dafttech.terra.game.world.tiles.TileTorch;
import com.dafttech.terra.game.world.tiles.TileWeed;

public class BiomeGrassland extends Biome {
    public BiomeGrassland(String name) {
        super(name);
    }

    public static Biome instance = new BiomeGrassland("Grassland");

    @Override
    public void generateTerrain(WorldGenerator gen) {
        PerlinNoise noise = gen.getNoise();
        
        for (int x = 0; x < gen.world.size.x; x++) {
            System.out.print(noise.perlinNoise(x / 100f));
            for (int y = (int) gen.world.size.y - 1; y > (1f + noise.perlinNoise(x / 150f)) * 200; y--) {
                new Position(x, y).setTile(gen.world, new TileDirt());
            }
        }
    }

    @Override
    public void populate(WorldGenerator gen) {
        // TODO Auto-generated method stub

    }
}
