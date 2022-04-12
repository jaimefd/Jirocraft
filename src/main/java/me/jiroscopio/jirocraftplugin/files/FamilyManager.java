package me.jiroscopio.jirocraftplugin.files;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.records.FamilyRecord;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;

public class FamilyManager extends FileManager{

    public FamilyManager(JirocraftPlugin plugin, String fileName) {
        super(plugin, fileName);
    }

    public void setupManager() {
        String base;
        int upgrade;
        ArrayList<String> tiers;
        ConfigurationSection tiers_section;

        for (String key : this.getConfig().getKeys(false)) {
            upgrade = 128;
            tiers = new ArrayList<>();

            if (this.getConfig().contains(key + ".base")) base = this.getConfig().getString(key + ".base");
            else continue;

            if (this.getConfig().contains(key + ".upgrade")) upgrade = this.getConfig().getInt(key + ".upgrade");

            if (this.getConfig().contains(key + ".tiers")) {
                tiers_section = this.getConfig().getConfigurationSection(key + ".tiers");
                assert tiers_section != null;
                for (String index : tiers_section.getKeys(false)) {
                    tiers.add(tiers_section.getString(index));
                }
            } else continue;

            FamilyRecord family = new FamilyRecord(key, base, upgrade, tiers);

            this.plugin.familyRecords.put(key, family);
        }

        System.out.println("FAMILIES LOADED");
    }
}
