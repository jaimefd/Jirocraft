package me.jiroscopio.jirocraftplugin.populators;

import me.jiroscopio.jirocraftplugin.helpers.OreGenerator;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Random;

public class AluminiumOrePopulator extends BlockPopulator {

    @Override
    public void populate(@NonNull WorldInfo worldInfo, @NonNull Random random, int x, int z, @NonNull LimitedRegion limitedRegion) {
        OreGenerator oreGenerator = new OreGenerator(random, limitedRegion, x, z);

        // Common Aluminium Ore, exclusive to Lush Caves
        int amount = 24;
        for (int i = 0; i < amount; i++) {
            int size = random.nextInt(7) + 4;
            int y = - random.nextInt(62) - 1;
            oreGenerator.populateOres(size, true, y, Material.DEEPSLATE, Material.BROWN_GLAZED_TERRACOTTA,
                    BlockFace.EAST, null, 0, Biome.LUSH_CAVES);
        }

        // Rare Aluminium Ore, appears everywhere
        amount = 8;
        for (int i = 0; i < amount; i++) {
            int size = random.nextInt(4) + 1;
            int y = - random.nextInt(62) - 1;
            oreGenerator.populateOres(size, true, y, Material.DEEPSLATE, Material.BROWN_GLAZED_TERRACOTTA, BlockFace.EAST, null, 0);
        }
    }

}
