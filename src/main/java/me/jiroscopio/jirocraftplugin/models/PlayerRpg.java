package me.jiroscopio.jirocraftplugin.models;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.helpers.ItemHelper;
import me.jiroscopio.jirocraftplugin.records.ItemRecord;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class PlayerRpg {

    private final Player bukkitPlayer;
    private JirocraftPlugin plugin;
    private Map<String,Float> stats = new HashMap<>();
    private PlayerEquipment equipment;

    private int level = 0; // The level of the player. Not lost on death, and not used as a resource, it's permanent
    private int experience = 0; // Used to increase levels, so the level is actually based on the experience
    private float health;
    private float mana;

    public PlayerRpg(Player player, JirocraftPlugin plugin) {
        this.bukkitPlayer = player;
        this.plugin = plugin;
        this.updateStats();
    }

    public Map<String,Float> getStats() {
        return stats;
    }

    public static PlayerRpg getRpgPlayer (Player player, JirocraftPlugin plugin) {
        if (plugin.rpgPlayers.containsKey(player.getUniqueId())) return plugin.rpgPlayers.get(player.getUniqueId());
        else return registerRpgPlayer(player, plugin);
    }

    public static PlayerRpg registerRpgPlayer (Player player, JirocraftPlugin plugin) {
        PlayerRpg newRpg = new PlayerRpg(player, plugin);
        plugin.rpgPlayers.put(player.getUniqueId(), newRpg);
        return newRpg;
    }

    public void initializeStats() {
        stats.clear();
        stats.put("max_health", 150F);
        stats.put("health_regen", 0.5F);
        stats.put("max_mana", 100F);
        stats.put("mana_regen", 5F);
        stats.put("crit_chance", 5F);
        stats.put("crit_damage", 50F);
        stats.put("evasion", 10F);
        stats.put("speed", 100F);
    }

    public void delayedUpdate() {
        new BukkitRunnable(){
            @Override
            public void run(){
                PlayerRpg.getRpgPlayer(bukkitPlayer, plugin).updateStats();
            }
        }.runTaskLater(plugin, 1);
    }

    public void updateStats() {
        this.initializeStats();
        PlayerInventory inv = this.bukkitPlayer.getInventory();
        for (ItemStack armor : inv.getArmorContents()) {
            addStats(armor, null);
        }
        addStats(inv.getItemInMainHand(), EquipmentSlot.HAND);
        addStats(inv.getItemInOffHand(), EquipmentSlot.OFF_HAND);
        this.applyStats();
    }

    public void applyStats() {
        float max_health = stats.getOrDefault("max_health", 0F) / 25;
        Objects.requireNonNull(bukkitPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(max_health);
    }

    public void addStats(ItemStack stack, EquipmentSlot slot) {
        if (stack == null) return;
        String item_type = ItemHelper.getItemType(this.plugin, stack);
        ItemRecord item_record = this.plugin.itemRecords.get(item_type);
        if (item_record == null) return;
        if (item_record.base_stats() == null) return;

        if (slot != null) {
            if (ItemHelper.isArmor(item_type, this.plugin)) return;
        }

        for (String stat_name : item_record.base_stats().keySet()) {
            if (!stats.containsKey(stat_name)) stats.put(stat_name, 0F);
            stats.replace(stat_name, stats.get(stat_name) + item_record.base_stats().get(stat_name));
        }
    }
}
