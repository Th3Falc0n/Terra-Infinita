package com.dafttech.terra.game.world.gen.biomes;

import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.game.world.Chunk;
import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.gen.WorldGenerator;
import com.dafttech.terra.game.world.gen.calc.PerlinNoise;
import com.dafttech.terra.game.world.subtiles.SubtileBone;
import com.dafttech.terra.game.world.subtiles.SubtileGrass;
import com.dafttech.terra.game.world.tiles.Tile;
import com.dafttech.terra.game.world.tiles.TileDirt;
import com.dafttech.terra.game.world.tiles.TileGrass;
import com.dafttech.terra.game.world.tiles.TileStone;
import com.dafttech.terra.resources.Options;

public class BiomeGrassland extends Biome {
    public BiomeGrassland(String name) {
        super(name);
    }

    public static Biome instance = new BiomeGrassland("Grassland");

    @Override
    public void generateTerrain(WorldGenerator gen) {
        PerlinNoise noise = gen.getNoise();

        for (int x = 0; x < gen.world.size.x; x++) {
            int h = (int) ((1f + noise.perlinNoise(x / 150f)) * 75);

            for (int y = gen.world.size.y - 1; y > h; y--) {
                Tile tile = null;

                if (y - 1 == h) {
                    tile = new TileGrass();
                }

                if (tile == null) {
                    if (y < (gen.world.size.y - h) / 5 + h) {
                        tile = new TileDirt();
                        if (y - 2 == h) {
                            tile.addSubtile(new SubtileGrass());
                        }
                    } else {
                        tile = new TileStone();
                        if (TerraInfinita.rnd.nextDouble() < 0.004) {
                            tile.addSubtile(new SubtileBone());
                        }
                    }
                }

                new Vector2i(x, y).setTile(gen.world, tile);
            }
        }
    }

    @Override
    public void populate(WorldGenerator gen) {
        // TODO Auto-generated method stub

    }

    @Override
    public void generateChunk(WorldGenerator gen, Chunk chunk) {
        PerlinNoise noise = gen.getNoise();
        
        Vector2i chunkPos = new Vector2i().getBlockInWorldPos(chunk);
        
        for (int x = chunkPos.x; x < chunkPos.x + chunk.world.chunksize.x + 1; x++) {
            int h = (int) ((1f + noise.perlinNoise(x / 150f)) * 75);

            for (int y = chunkPos.y + chunk.world.chunksize.y; y >= chunkPos.y; y--) {
                if (y <= h) break;
                
                Tile tile = null;

                if (y - 1 == h) {
                    tile = new TileGrass();
                }

                if (tile == null) {
                    if (y < (gen.world.size.y - h) / 5 + h) {
                        tile = new TileDirt();
                        if (y - 2 == h) {
                            tile.addSubtile(new SubtileGrass());
                        }
                    } else {
                        tile = new TileStone();
                        if (TerraInfinita.rnd.nextDouble() < 0.004) {
                            tile.addSubtile(new SubtileBone());
                        }
                    }
                }

                new Vector2i(x, y).setTileWithoutGen(gen.world, tile);
            }
        }
    }

    @Override
    public void populateChunk(WorldGenerator gen, Chunk chunk) {
        // TODO Auto-generated method stub

    }
}
