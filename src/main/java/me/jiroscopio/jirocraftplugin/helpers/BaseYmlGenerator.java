package me.jiroscopio.jirocraftplugin.helpers;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class BaseYmlGenerator {

    public static void generateItemYml (JirocraftPlugin plugin) {
        FileConfiguration fileConfig = plugin.itemManager.getConfig();
        for (Material mat : Material.values()) {
            ConfigurationSection section = fileConfig.createSection(mat.toString());
            section.set("material", mat.toString());
        }
        plugin.itemManager.saveConfig();
    }

}
