package me.jiroscopio.jirocraftplugin.populators;

import me.jiroscopio.jirocraftplugin.helpers.OreGenerator;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Random;

public class LeadOrePopulator extends BlockPopulator {

    @Override
    public void populate(@NonNull WorldInfo worldInfo, @NonNull Random random, int x, int z, @NonNull LimitedRegion limitedRegion) {
        OreGenerator oreGenerator = new OreGenerator(random, limitedRegion, x, z);
        int amount = 4;
        for (int i = 0; i < amount; i++) {
            int size = random.nextInt(9) + 24;
            int y = - random.nextInt(30) - random.nextInt(30) - 1;
            oreGenerator.populateOres(size, false, y, Material.DEEPSLATE, Material.CYAN_GLAZED_TERRACOTTA, BlockFace.NORTH, null, 0);
        }
    }

}
