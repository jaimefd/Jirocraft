package me.jiroscopio.jirocraftplugin.populators;

import me.jiroscopio.jirocraftplugin.helpers.OreGenerator;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Random;

public class UraniumOrePopulator extends BlockPopulator {

    @Override
    public void populate(@NonNull WorldInfo worldInfo, Random random, int x, int z, @NonNull LimitedRegion limitedRegion) {
        int URANIUM_CHUNK_CHANCE = 5;
        if (random.nextInt(100) < URANIUM_CHUNK_CHANCE) {
            OreGenerator oreGenerator = new OreGenerator(random, limitedRegion, x, z);
            int amount = 8;
            for (int i = 0; i < amount; i++) {
                int y = random.nextInt(32) + 16;
                oreGenerator.populateOres(1, true, y, Material.STONE, Material.GREEN_GLAZED_TERRACOTTA, BlockFace.WEST, Material.AIR, -100);
            }
        }
    }

}
