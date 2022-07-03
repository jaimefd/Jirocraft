package me.jiroscopio.jirocraftplugin.models;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.helpers.ItemHelper;
import me.jiroscopio.jirocraftplugin.records.ItemRecord;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RpgEntity {

    private LivingEntity bukkitEntity;
    private JirocraftPlugin plugin;
    private Map<String,Float> stats = new HashMap<>();

    private float health;

    public RpgEntity(LivingEntity entity, JirocraftPlugin plugin) {
        this.bukkitEntity = entity;
        this.plugin = plugin;
    }

    public Map<String,Float> getStats() {
        return stats;
    }

    public static RpgEntity getRpgEntity (LivingEntity entity, JirocraftPlugin plugin) {
        if (plugin.rpgEntities.containsKey(entity.getUniqueId())) return plugin.rpgEntities.get(entity.getUniqueId());
        else return registerRpgEntity(entity, plugin);
    }

    public static RpgEntity registerRpgEntity (LivingEntity entity, JirocraftPlugin plugin) {
        RpgEntity newRpg = new RpgEntity(entity, plugin);
        plugin.rpgEntities.put(entity.getUniqueId(), newRpg);
        return newRpg;
    }

    public void initializeStats() {
        stats.clear();
        stats.put("max_health", 80F);
        stats.put("speed", 100F);
    }

    public void delayedUpdate() {
        new BukkitRunnable(){
            @Override
            public void run(){
                RpgEntity.getRpgEntity(bukkitEntity, plugin).updateStats();
            }
        }.runTaskLater(plugin, 1);
    }

    public void updateStats() {
        this.initializeStats();
        EntityEquipment equipment = this.bukkitEntity.getEquipment();
        if (equipment != null) {
            for (ItemStack armor : equipment.getArmorContents()) {
                addStats(armor, null);
            }
            addStats(equipment.getItemInMainHand(), EquipmentSlot.HAND);
            addStats(equipment.getItemInOffHand(), EquipmentSlot.OFF_HAND);
        }
        this.applyStats();
    }

    public void applyStats() {
        float max_health = stats.getOrDefault("max_health", 0F) / 25;
        Objects.requireNonNull(bukkitEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(max_health);
    }

    public void addStats(ItemStack stack, EquipmentSlot slot) {
        if (stack == null) return;
        if (stack.getType().equals(Material.AIR)) return;
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
