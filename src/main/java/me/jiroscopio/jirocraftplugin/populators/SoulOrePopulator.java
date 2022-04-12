package me.jiroscopio.jirocraftplugin.populators;

import me.jiroscopio.jirocraftplugin.helpers.OreGenerator;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Random;

public class SoulOrePopulator extends BlockPopulator {

    @Override
    public void populate(@NonNull WorldInfo worldInfo, @NonNull Random random, int x, int z, @NonNull LimitedRegion limitedRegion) {
        OreGenerator oreGenerator = new OreGenerator(random, limitedRegion, x, z);
        int amount = 16;
        for (int i = 0; i < amount; i++) {
            int y = random.nextInt(128);
            oreGenerator.populateOres(1, true, y, Material.SOUL_SAND, Material.GRAY_GLAZED_TERRACOTTA, BlockFace.WEST, null, 0);
        }

        // Four times as common if buried

        amount = 48;
        for (int i = 0; i < amount; i++) {
            int y = random.nextInt(128);
            oreGenerator.populateOres(1, true, y, Material.SOUL_SAND, Material.GRAY_GLAZED_TERRACOTTA, BlockFace.WEST, Material.AIR, -100);
        }
    }

}
