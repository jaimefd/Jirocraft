package me.jiroscopio.jirocraftplugin.files;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.enums.ItemType;
import me.jiroscopio.jirocraftplugin.records.ItemRecord;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
        ArrayList<String> lore;
        ArrayList<String> tags;
        String family;
        ItemType type;
        String head_owner; String head_value;
        Map<String,Float> stats;
        ConfigurationSection stats_section;
        int damage, damage_variance;

        for (String key : this.getConfig().getKeys(false)) {
            name = null;
            material = Material.getMaterial(key);
            value = 0;
            currency = 0;
            rarity = -1;
            modelData = 0;
            lore = new ArrayList<>();
            family = null;
            type = ItemType.ANY;
            tool_power = 0;
            tags = new ArrayList<>();
            head_owner = null; head_value = null;
            stats = null;
            damage = 5;
            damage_variance = 0;

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
                lore = (ArrayList<String>) this.getConfig().getStringList(key + ".lore");
            if (this.getConfig().contains(key + ".tags")) {
                tags.addAll(this.getConfig().getStringList(key + ".tags"));
            }
            if (this.getConfig().contains(key + ".family"))
                family = this.getConfig().getString(key + ".family");
            if (this.getConfig().contains(key + ".head.owner"))
                head_owner = this.getConfig().getString(key + ".head.owner");
            if (this.getConfig().contains(key + ".head.value"))
                head_value = this.getConfig().getString(key + ".head.value");
            if (this.getConfig().contains(key + ".type")) {
                String tool_value = this.getConfig().getString(key + ".type");
                if (tool_value != null) {
                    if (tool_value.equals("NONE")) type = null;
                    else type = ItemType.valueOf(tool_value);
                }
            }
            if (this.getConfig().contains(key + ".tool_power"))
                tool_power = this.getConfig().getInt(key + ".tool_power");

            if (this.getConfig().contains(key + ".stats")) {
                stats_section = this.getConfig().getConfigurationSection(key + ".stats");
                if (stats_section == null) continue;
                stats = new HashMap<>();
                for (String stat : stats_section.getKeys(false)) {
                    stats.put(stat, Float.parseFloat(Objects.requireNonNull(stats_section.getString(stat))));
                }
            }

            if (this.getConfig().contains(key + ".damage"))
                damage = this.getConfig().getInt(key + ".damage");
            if (this.getConfig().contains(key + ".damage_variance"))
                damage_variance = this.getConfig().getInt(key + ".damage_variance");

            ItemRecord item = new ItemRecord(key, name, material, value, currency, rarity,
                    modelData, lore, tags, family, type, tool_power, head_owner, head_value, stats, damage, damage_variance);

            this.plugin.itemRecords.put(key, item);
        }

        System.out.println("ITEMS LOADED");
    }

}
