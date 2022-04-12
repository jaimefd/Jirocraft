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

public class RubyOrePopulator extends BlockPopulator {

    @Override
    public void populate(@NonNull WorldInfo worldInfo, @NonNull Random random, int x, int z, @NonNull LimitedRegion limitedRegion) {
        OreGenerator oreGenerator = new OreGenerator(random, limitedRegion, x, z);
        int amount = 128;
        for (int i = 0; i < amount; i++) {
            int y = random.nextInt(128);
            oreGenerator.populateOres(1, true, y, Material.BLACKSTONE,
                    Material.GRAY_GLAZED_TERRACOTTA, BlockFace.NORTH, null, 0, Biome.BASALT_DELTAS);
        }
    }

}
