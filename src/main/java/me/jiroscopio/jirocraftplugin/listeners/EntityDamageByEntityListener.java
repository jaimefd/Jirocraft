package me.jiroscopio.jirocraftplugin.listeners;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.managers.CombatManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.PlayerInventory;

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
                        inv.setHeldItemSlot(0);
                    } else {
                        // here we attacc
                        if (defender instanceof LivingEntity le) {
                            double final_damage = CombatManager.playerMeleeAttack((Player) attacker, defender, inv.getItemInMainHand(), plugin);
                            e.setDamage(0);
                            le.setHealth(le.getHealth() - final_damage);
                        }
                    }
                }
            }
            case ENTITY_SWEEP_ATTACK -> System.out.println("oops I hit you on accident");
            default ->
                    System.out.println("The type of cause of entity damage " + e.getCause() + " is not supported.");
        }
    }

}
