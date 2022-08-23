package me.jiroscopio.jirocraftplugin.listeners;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import org.apache.commons.text.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawnListener implements Listener {

    private final JirocraftPlugin plugin;

    public CreatureSpawnListener(JirocraftPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        LivingEntity le = e.getEntity();
        if (le.getType().equals(EntityType.ARMOR_STAND)) return;

        int actual_health = (int) Math.round(le.getHealth() * 25);
        //int max_health = (int) Math.round(Objects.requireNonNull(le.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue() * 25);
        String health_name = "§b" + actual_health + "/" + actual_health + "§a❤";
        String mob_name = "§b§l Lv.1 " + ChatColor.GOLD + WordUtils.capitalize(le.getType().toString().replace('_', ' ').toLowerCase());
        le.setCustomNameVisible(true);
        le.setCustomName(mob_name + " " + ChatColor.RESET + health_name);
    }

}
