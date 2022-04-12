package me.jiroscopio.jirocraftplugin.populators;

import me.jiroscopio.jirocraftplugin.helpers.OreGenerator;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Random;

public class VoidOrePopulator extends BlockPopulator {

    @Override
    public void populate(@NonNull WorldInfo worldInfo, Random random, int x, int z, @NonNull LimitedRegion limitedRegion) {
        int VOID_CHUNK_CHANCE = 5;
        if (random.nextInt(100) < VOID_CHUNK_CHANCE) {
            OreGenerator oreGenerator = new OreGenerator(random, limitedRegion, x, z);
            int y = random.nextInt(21) + 2;
            oreGenerator.populateOres(1, true, y, Material.END_STONE, Material.LIGHT_GRAY_GLAZED_TERRACOTTA, BlockFace.WEST, null, 0);
        }
    }

}
