package me.jiroscopio.jirocraftplugin.files;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.enums.ToolType;
import me.jiroscopio.jirocraftplugin.records.BlockRecord;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;

public class BlockManager extends FileManager {

    public BlockManager(JirocraftPlugin plugin, String fileName) {
        super(plugin, fileName);
    }

    public void setupManager() {
        ArrayList<String> drops;
        ToolType tool;
        int tool_tier;
        Material source;
        BlockFace facing;

        for (String key : this.getConfig().getKeys(false)) {
            drops = new ArrayList<>();
            tool = ToolType.ANY;
            tool_tier = 0;
            source = null;
            facing = null;

            if (this.getConfig().contains(key + ".tool_tier"))
                tool_tier = this.getConfig().getInt(key + ".tool_tier");

            // If the drops from the item are not obtainable from any drop, tool is NONE and is set to null
            // If anything (even the hand) can get the drops, tool is set to ToolType.ANY
            if (this.getConfig().contains(key + ".tool")) {
                String tool_value = this.getConfig().getString(key + ".tool");
                if (tool_value != null) {
                    if (tool_value.equals("NONE")) tool = null;
                    else tool = ToolType.valueOf(tool_value);
                }
            }

            if (this.getConfig().contains(key + ".source")) {
                String source_value = this.getConfig().getString(key + ".source");
                source = Material.valueOf(source_value);
            }

            if (this.getConfig().contains(key + ".facing")) {
                String facing_value = this.getConfig().getString(key + ".facing");
                facing = BlockFace.valueOf(facing_value);
            }

            if (this.getConfig().contains(key + ".drops")) {
                drops.addAll(this.getConfig().getStringList(key + ".drops"));
            }

            BlockRecord block = new BlockRecord(key, tool, tool_tier, drops, source, facing);

            this.plugin.blockRecords.put(key, block);
        }

        System.out.println("BLOCKS LOADED");
    }

}
