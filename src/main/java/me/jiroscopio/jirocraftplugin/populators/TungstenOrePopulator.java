package me.jiroscopio.jirocraftplugin.populators;

import me.jiroscopio.jirocraftplugin.helpers.OreGenerator;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Random;

public class TungstenOrePopulator extends BlockPopulator {

    @Override
    public void populate(@NonNull WorldInfo worldInfo, Random random, int x, int z, @NonNull LimitedRegion limitedRegion) {
        int TUNGSTEN_CHUNK_CHANCE = 10;
        if (random.nextInt(100) < TUNGSTEN_CHUNK_CHANCE) {
            OreGenerator oreGenerator = new OreGenerator(random, limitedRegion, x, z);

            int y = random.nextInt(316) - 60;

            if (y < 0) {
                oreGenerator.populateOres(1, true, y, Material.DEEPSLATE,
                        Material.BLUE_GLAZED_TERRACOTTA, BlockFace.WEST, null, 0);
            } else {
                oreGenerator.populateOres(1, true, y, Material.STONE,
                        Material.BLUE_GLAZED_TERRACOTTA, BlockFace.SOUTH, null, 0);
            }
        }
    }

}
