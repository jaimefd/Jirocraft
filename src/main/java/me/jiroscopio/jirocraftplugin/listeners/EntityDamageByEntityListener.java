package me.jiroscopio.jirocraftplugin.listeners;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.managers.CombatManager;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

public class EntityDamageByEntityListener implements Listener {

    private final JirocraftPlugin plugin;

    public EntityDamageByEntityListener(JirocraftPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        switch (e.getCause()) {
            case ENTITY_ATTACK -> {
                Entity attacker = e.getDamager();
                Entity defender = e.getEntity();
                if (attacker instanceof Player) {
                    PlayerInventory inv = ((Player) attacker).getInventory();
                    if (inv.getHeldItemSlot() > 0) {
                        e.setCancelled(true);
                        attacker.sendMessage(ChatColor.DARK_RED + "You can only attack with the item in your first slot!");
                        //inv.setHeldItemSlot(0);
                    } else {
                        // here we attacc
                        if (defender instanceof LivingEntity le) {
                            CombatManager.DamageInstance di = CombatManager.playerMeleeAttack((Player) attacker, defender, inv.getItemInMainHand(), plugin);
                            double final_damage = (double) di.damage / 25;
                            e.setDamage(0);
                            le.setHealth(Math.max(le.getHealth() - final_damage, 0));
                            String leName = le.getCustomName();

                            // update health in name
                            if (leName != null) {
                                if (leName.endsWith("❤")) {
                                    int new_health = (int) Math.round(le.getHealth() * 25);
                                    AttributeInstance max_health_attr = le.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                                    String health_name = "§b" + new_health + "/";
                                    if (max_health_attr == null)
                                        health_name += leName.split("/")[1];
                                    else {
                                        int max_health = (int) Math.round(max_health_attr.getValue() * 25);
                                        System.out.println("New hp: " + new_health + " Max hp: " +  max_health + " Division: " + ((double)new_health/max_health));
                                        if (((double)new_health/max_health) < 0.2) health_name += max_health + "§4❤";
                                        else if (((double)new_health/max_health) < 0.5) health_name += max_health + "§e❤";
                                        else health_name += max_health + "§a❤";
                                    }
                                    String mob_name = "§b§l Lv.1 " + ChatColor.GOLD + WordUtils.capitalize(le.getType().toString().replace('_', ' ').toLowerCase());
                                    le.setCustomName(mob_name + " " + ChatColor.RESET + health_name);
                                    //if (le.getHealth() <= 0) pass.remove();
                                }
                            }

                            // show damage indicator
                            String damage_indicator = (di.crit ? "§7§l" : "§7") + -di.damage + " ≡";
                            Location loc = defender.getLocation().clone().add(getRandomOffset(), 1, getRandomOffset());
                            ArmorStand as = defender.getWorld().spawn(loc, ArmorStand.class, ar -> ar.setInvisible(true));
                            as.setCustomName(damage_indicator);
                            as.setInvulnerable(true);
                            as.setSmall(true);
                            as.setGravity(false);
                            as.setCustomNameVisible(true);
                            as.setMarker(true);
                            as.setVisible(true);

                            // 20 ticks
                            int INDICATOR_DURATION = 20;
                            new BukkitRunnable(){
                                @Override
                                public void run(){
                                    as.remove();
                                }
                            }.runTaskLater(plugin, INDICATOR_DURATION);
                        }
                    }
                }
            }
            case ENTITY_SWEEP_ATTACK -> System.out.println("oops I hit you on accident");
            default ->
                    System.out.println("The type of cause of entity damage " + e.getCause() + " is not supported.");
        }
    }

    private double getRandomOffset() {
        double random = Math.random();
        if (Math.random() < 0.5) random *= -1;
        return random;
    }
}
