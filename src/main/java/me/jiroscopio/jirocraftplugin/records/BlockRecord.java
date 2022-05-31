package me.jiroscopio.jirocraftplugin.records;

import me.jiroscopio.jirocraftplugin.enums.ItemType;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;

public record BlockRecord(String id, ItemType tool, int tool_tier, ArrayList<String> drops, Material source_block, BlockFace facing) {
}
