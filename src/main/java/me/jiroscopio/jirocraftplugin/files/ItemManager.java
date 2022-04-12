package me.jiroscopio.jirocraftplugin.files;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.enums.ToolType;
import me.jiroscopio.jirocraftplugin.records.ItemRecord;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Objects;

public class ItemManager extends FileManager{

    public ItemManager(JirocraftPlugin plugin, String fileName) {
        super(plugin, fileName);
    }

    public void setupManager() {
        String name;
        Material material;
        int value;
        int currency;
        int rarity;
        int modelData;
        int tool_power;
        String lore;
        ArrayList<String> tags;
        String family;
        ToolType tool_type;

        for (String key : this.getConfig().getKeys(false)) {
            name = null;
            material = Material.getMaterial(key);
            value = 0;
            currency = 0;
            rarity = 0;
            modelData = 0;
            lore = null;
            family = null;
            tool_type = ToolType.ANY;
            tool_power = 0;
            tags = new ArrayList<>();

            if (this.getConfig().contains(key + ".name"))
                name = this.getConfig().getString(key + ".name");
            if (this.getConfig().contains(key + ".material")) {
                material = Material.getMaterial(Objects.requireNonNull(this.getConfig().getString(key + ".material")));
            }
            if (this.getConfig().contains(key + ".value"))
                value = this.getConfig().getInt(key + ".value");
            if (this.getConfig().contains(key + ".currency"))
                currency = this.getConfig().getInt(key + ".currency");
            if (this.getConfig().contains(key + ".rarity"))
                rarity = this.getConfig().getInt(key + ".rarity");
            if (this.getConfig().contains(key + ".modelData"))
                modelData = this.getConfig().getInt(key + ".modelData");
            if (this.getConfig().contains(key + ".lore"))
                lore = this.getConfig().getString(key + ".lore");
            if (this.getConfig().contains(key + ".tags")) {
                tags.addAll(this.getConfig().getStringList(key + ".tags"));
            }
            if (this.getConfig().contains(key + ".family"))
                family = this.getConfig().getString(key + ".family");
            if (this.getConfig().contains(key + ".tool_type")) {
                String tool_value = this.getConfig().getString(key + ".tool_type");
                if (tool_value != null) {
                    if (tool_value.equals("NONE")) tool_type = null;
                    else tool_type = ToolType.valueOf(tool_value);
                }
            }
            if (this.getConfig().contains(key + ".tool_power"))
                tool_power = this.getConfig().getInt(key + ".tool_power");

            ItemRecord item = new ItemRecord(key, name, material, value, currency, rarity, modelData, lore, tags, family, tool_type, tool_power);

            this.plugin.itemRecords.put(key, item);
        }

        System.out.println("ITEMS LOADED");
    }

}
