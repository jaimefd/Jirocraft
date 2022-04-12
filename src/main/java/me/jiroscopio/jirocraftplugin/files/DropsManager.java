package me.jiroscopio.jirocraftplugin.files;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.helpers.NumberGenerator;
import me.jiroscopio.jirocraftplugin.records.drops.DropEntry;
import me.jiroscopio.jirocraftplugin.records.drops.DropPool;
import me.jiroscopio.jirocraftplugin.records.drops.DropsRecord;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;

public class DropsManager extends FileManager {

    public DropsManager(JirocraftPlugin plugin, String fileName) {
        super(plugin, fileName);
    }

    public void setupManager() {
        ArrayList<DropPool> pools;
        ArrayList<DropEntry> entries;
        DropPool pool;
        DropEntry entry;
        NumberGenerator rolls, amount;
        ConfigurationSection pools_config, entries_config;
        String number_type, entry_type, entry_id;
        int min, max, number_chance, weight, announce, total_weight, tool_power_min, tool_power_max;
        double pool_chance, luck_multiplier, looting_multiplier;
        boolean tool, tiered;

        for (String key : this.getConfig().getKeys(false)) {
            pools = new ArrayList<>();
            pools_config = this.getConfig().getConfigurationSection(key);
            if (pools_config == null) continue;
            for (String pool_key : pools_config.getKeys(false)) {
                pool_chance = 100.0;
                tool = false;
                rolls = null;
                luck_multiplier = 1.0; looting_multiplier = 1.0;
                entries = new ArrayList<>();
                entry = null;
                total_weight = 0;
                tool_power_min = 0; tool_power_max = 0;

                if (pools_config.contains(pool_key + ".tool")) tool = pools_config.getBoolean(pool_key + ".tool");
                if (pools_config.contains(pool_key + ".tool_power_min")) tool_power_min = pools_config.getInt(pool_key + ".tool_power_min");
                if (pools_config.contains(pool_key + ".tool_power_max")) tool_power_max = pools_config.getInt(pool_key + ".tool_power_max");
                if (pools_config.contains(pool_key + ".chance")) pool_chance = (pools_config.getDouble(pool_key + ".chance") / 100);
                if (pools_config.contains(pool_key + ".luck_multiplier")) luck_multiplier = pools_config.getDouble(pool_key + ".luck_multiplier");
                if (pools_config.contains(pool_key + ".looting_multiplier")) looting_multiplier = pools_config.getDouble(pool_key + ".looting_multiplier");

                if (pools_config.contains(pool_key + ".rolls")) {
                    number_type = "uniform";
                    min = 1; max = 1; number_chance = 0;

                    if (pools_config.contains(pool_key + ".rolls.type")) number_type = pools_config.getString(pool_key + ".rolls.type");
                    if (pools_config.contains(pool_key + ".rolls.min")) min = pools_config.getInt(pool_key + ".rolls.min");
                    if (pools_config.contains(pool_key + ".rolls.max")) max = pools_config.getInt(pool_key + ".rolls.max");
                    if (pools_config.contains(pool_key + ".rolls.chance")) number_chance = pools_config.getInt(pool_key + ".rolls.chance");

                    rolls = new NumberGenerator(number_type, min, max, number_chance);
                }

                if (pools_config.contains(pool_key + ".entry")) {
                    entry_type = "item";
                    weight = 1;
                    amount = null;
                    announce = 0;
                    tiered = true;

                    if (pools_config.contains(pool_key + ".entry.type")) entry_type = pools_config.getString(pool_key + ".entry.type");
                    if (pools_config.contains(pool_key + ".entry.item")) entry_id = pools_config.getString(pool_key + ".entry.item");
                    else continue;
                    if (pools_config.contains(pool_key + ".entry.weight")) weight = pools_config.getInt(pool_key + ".entry.weight");
                    if (pools_config.contains(pool_key + ".entry.announce")) announce = pools_config.getInt(pool_key + ".entry.announce");
                    if (pools_config.contains(pool_key + ".entry.tiered")) tiered = pools_config.getBoolean(pool_key + ".entry.tiered");

                    if (pools_config.contains(pool_key + ".entry.amount")) {
                        number_type = "uniform";
                        min = 1; max = 1; number_chance = 0;

                        if (pools_config.contains(pool_key + ".entry.amount.type")) number_type = pools_config.getString(pool_key + ".entry.amount.type");
                        if (pools_config.contains(pool_key + ".entry.amount.min")) min = pools_config.getInt(pool_key + ".entry.amount.min");
                        if (pools_config.contains(pool_key + ".entry.amount.max")) max = pools_config.getInt(pool_key + ".entry.amount.max");
                        if (pools_config.contains(pool_key + ".entry.amount.number_chance")) number_chance = pools_config.getInt(pool_key + ".entry.amount.number_chance");

                        amount = new NumberGenerator(number_type, min, max, number_chance);
                    }
                    total_weight += weight;

                    entry = new DropEntry(entry_id, entry_type, announce, amount, weight, tiered);
                } else if (pools_config.contains(pool_key + ".entries")) {
                    entries_config = pools_config.getConfigurationSection(pool_key + ".entries");
                    if (entries_config == null) continue;
                    for (String entry_index : entries_config.getKeys(false)) {
                        entry_type = "item";
                        weight = 1;
                        amount = null;
                        announce = 0;
                        tiered = true;

                        if (entries_config.contains(entry_index + ".type")) entry_type = entries_config.getString(entry_index + ".type");
                        if (entries_config.contains(entry_index + ".item")) entry_id = entries_config.getString(entry_index + ".item");
                        else continue;
                        if (entries_config.contains(entry_index + ".weight")) weight = entries_config.getInt(entry_index + ".weight");
                        if (entries_config.contains(entry_index + ".announce")) announce = entries_config.getInt(entry_index + ".announce");
                        if (entries_config.contains(entry_index + ".tiered")) tiered = entries_config.getBoolean(entry_index + ".tiered");

                        if (entries_config.contains(entry_index + ".amount")) {
                            number_type = "uniform";
                            min = 1; max = 1; number_chance = 0;

                            if (entries_config.contains(entry_index + ".amount.type")) number_type = entries_config.getString(entry_index + ".amount.type");
                            if (entries_config.contains(entry_index + ".amount.min")) min = entries_config.getInt(entry_index + ".amount.min");
                            if (entries_config.contains(entry_index + ".amount.max")) max = entries_config.getInt(entry_index + ".amount.max");
                            if (entries_config.contains(entry_index + ".amount.number_chance")) number_chance = entries_config.getInt(entry_index + ".amount.number_chance");

                            amount = new NumberGenerator(number_type, min, max, number_chance);
                        }
                        total_weight += weight;

                        entries.add(new DropEntry(entry_id, entry_type, announce, amount, weight, tiered));
                    }
                } else continue;

                pools.add(new DropPool(pool_chance, tool, tool_power_min, tool_power_max, rolls, luck_multiplier, looting_multiplier, entries, entry, total_weight));
            }
            DropsRecord drops = new DropsRecord(key, pools);

            this.plugin.dropRecords.put(key, drops);
        }
        System.out.println("DROPS LOADED");
    }
}
