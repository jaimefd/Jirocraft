package me.jiroscopio.jirocraftplugin.helpers;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public record OreGenerator(Random random, LimitedRegion limitedRegion, int x, int z) {

    public static final List<Material> airMaterials = Arrays.asList(Material.AIR, Material.CAVE_AIR, Material.VOID_AIR, Material.WATER, Material.LAVA);

    public void populateOres(int size, boolean cluster, int original_y, Material baseBlock, Material oreBlock, BlockFace direction,
                             Material nextToBlock, int nextToChance, Biome... oreBiomes) {
        // Need to transform chunk coordinates into block coordinates
        int original_x = random.nextInt(16) + 16 * x;
        int original_z = random.nextInt(16) + 16 * z;

        // Which rare ore is it
        String name_ore = null;
        //if (oreBlock == Material.BROWN_GLAZED_TERRACOTTA && direction == BlockFace.NORTH) name_ore = "chromium ore";
        if (oreBlock == Material.LIGHT_GRAY_GLAZED_TERRACOTTA && direction == BlockFace.WEST) name_ore = "void ore";
        //if (oreBlock == Material.BLUE_GLAZED_TERRACOTTA && (direction == BlockFace.WEST || direction == BlockFace.SOUTH)) name_ore = "tungsten ore";

        if (original_y >= 320) return;

        // Don't continue if the first block doesn't fit the base block, we might be very far away from it
        if (limitedRegion.getType(original_x, original_y, original_z) != baseBlock) return;

        // If provided a specific biome, only continue if the first block chosen is in that biome
        if (oreBiomes.length > 0)
            if (Arrays.stream(oreBiomes).noneMatch(biome -> biome.equals(limitedRegion.getBiome(original_x, original_y, original_z)))) return;

        // If specified a block it has to be next with, check it
        if (nextToBlock != null) {
            if (nextToChance > 0) {
                boolean found = limitedRegion.getType(original_x + 1, original_y, original_z) == nextToBlock;
                if (limitedRegion.getType(original_x - 1, original_y, original_z) == nextToBlock) found = true;
                if (limitedRegion.getType(original_x, original_y + 1, original_z) == nextToBlock) found = true;
                if (limitedRegion.getType(original_x, original_y - 1, original_z) == nextToBlock) found = true;
                if (limitedRegion.getType(original_x, original_y, original_z + 1) == nextToBlock) found = true;
                if (limitedRegion.getType(original_x, original_y, original_z - 1) == nextToBlock) found = true;

                if (nextToBlock == Material.AIR && !found) {
                    if (airMaterials.contains(limitedRegion.getType(original_x + 1, original_y, original_z))) found = true;
                    if (airMaterials.contains(limitedRegion.getType(original_x - 1, original_y, original_z))) found = true;
                    if (airMaterials.contains(limitedRegion.getType(original_x, original_y + 1, original_z))) found = true;
                    if (airMaterials.contains(limitedRegion.getType(original_x, original_y - 1, original_z))) found = true;
                    if (airMaterials.contains(limitedRegion.getType(original_x, original_y, original_z + 1))) found = true;
                    if (airMaterials.contains(limitedRegion.getType(original_x, original_y, original_z - 1))) found = true;
                }

                if (!found) return;
            } else if (nextToChance < 0) {
                if (limitedRegion.getType(original_x + 1, original_y, original_z) == nextToBlock) return;
                if (limitedRegion.getType(original_x - 1, original_y, original_z) == nextToBlock) return;
                if (limitedRegion.getType(original_x, original_y + 1, original_z) == nextToBlock) return;
                if (limitedRegion.getType(original_x, original_y - 1, original_z) == nextToBlock) return;
                if (limitedRegion.getType(original_x, original_y, original_z + 1) == nextToBlock) return;
                if (limitedRegion.getType(original_x, original_y, original_z - 1) == nextToBlock) return;
            }
        }

        int temp_x = original_x;
        int temp_y = original_y;
        int temp_z = original_z;

        for (int i = 0; i < size; i++) {
            // first we try to generate the ore in the given location
            if (limitedRegion.getType(temp_x, temp_y, temp_z) == baseBlock) {
                limitedRegion.setType(temp_x, temp_y, temp_z, oreBlock);
                BlockData bd = limitedRegion.getBlockData(temp_x, temp_y, temp_z);
                if (direction != null && bd instanceof Directional) {
                    ((Directional) bd).setFacing(direction);
                    limitedRegion.setBlockData(temp_x, temp_y, temp_z, bd);
                }
                if (name_ore != null)
                    System.out.println("The ore " + name_ore + " has been spawned at " + temp_x + " " + temp_y + " " + temp_z);
            }

            if (size == 1) break;

            // now we get the next coordinates

            // we try at most 5 times to get good coordinates
            int attempts = 5;

            do {
                // by default, we generate in a 3x3x3 cube, but for big veins, we'll generate in a 5x5x5 cube instead
                if (cluster) {
                    if (size <= 20) {
                        temp_x = original_x + random.nextInt(3) - 1;
                        temp_y = original_y + random.nextInt(3) - 1;
                        temp_z = original_z + random.nextInt(3) - 1;
                    } else {
                        temp_x = original_x + random.nextInt(5) - 2;
                        temp_y = original_y + random.nextInt(5) - 2;
                        temp_z = original_z + random.nextInt(5) - 1;
                    }
                } else { // for spread ores, we use a big 9x9x9 cube
                    temp_x = original_x + random.nextInt(9) - 4;
                    temp_y = original_y + random.nextInt(9) - 4;
                    temp_z = original_z + random.nextInt(9) - 4;
                }
                attempts--;
            } while (attempts > 0 && limitedRegion.getType(temp_x, temp_y, temp_z) != baseBlock);

            // we don't check if we pick the same location twice, to make it so that larger veins are rarer
        }
    }



}
