package me.jiroscopio.jirocraftplugin.populators;

import me.jiroscopio.jirocraftplugin.helpers.OreGenerator;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Random;

public class CinnabarOrePopulator extends BlockPopulator {

    @Override
    public void populate(@NonNull WorldInfo worldInfo, @NonNull Random random, int x, int z, @NonNull LimitedRegion limitedRegion) {
        OreGenerator oreGenerator = new OreGenerator(random, limitedRegion, x, z);
        int amount = 16;
        for (int i = 0; i < amount; i++) {
            int size = random.nextInt(7) + 2;
            int y = - random.nextInt(62) - 1;
            oreGenerator.populateOres(size, true, y, Material.DEEPSLATE, Material.LIGHT_GRAY_GLAZED_TERRACOTTA, BlockFace.NORTH, null, 0);
        }
    }

}
