package me.jiroscopio.jirocraftplugin.populators;

import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.bukkit.inventory.meta.BlockDataMeta;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Random;

public class TestPopulator extends BlockPopulator {

    @Override
    public void populate(@NonNull WorldInfo worldInfo, @NonNull Random random, int x, int z, @NonNull LimitedRegion limitedRegion) {
        if (random.nextBoolean()) {
            int amount = random.nextInt(4)+1;  // Amount of trees
            for (int i = 0; i < amount; i++) {
                int height = random.nextInt(3)+1;
                int X = random.nextInt(16) + 16*x;
                int Z = random.nextInt(16) + 16*z;
                int Y = worldInfo.getMaxHeight()-1;
                while (limitedRegion.getType(X, Y, Z) == Material.AIR && Y > 0) Y--;
                for (int j = 0; j < height; j++) {
                    int y_coord = Y + j + 1;
                    if (limitedRegion.isInRegion(X, y_coord, Z)) limitedRegion.setType(X, y_coord, Z, Material.RED_GLAZED_TERRACOTTA);
                }
            }
        }
    }

}
