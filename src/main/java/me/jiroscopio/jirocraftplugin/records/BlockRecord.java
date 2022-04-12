package me.jiroscopio.jirocraftplugin.records;

import me.jiroscopio.jirocraftplugin.enums.ToolType;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;

public record BlockRecord(String id, ToolType tool, int tool_tier, ArrayList<String> drops, Material source_block, BlockFace facing) {
}
