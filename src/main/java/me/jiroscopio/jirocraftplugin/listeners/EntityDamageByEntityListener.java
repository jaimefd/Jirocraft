package me.jiroscopio.jirocraftplugin.listeners;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.managers.CombatManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
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
        Entity attacker = e.getDamager();
        Entity defender = e.getEntity();

        if (defender instanceof LivingEntity le) {
            boolean damagedAlready = false;
            CombatManager.DamageInstance di = new CombatManager.DamageInstance();
            di.damage = (int) Math.round(e.getDamage() * 25);
            di.crit = false;

            if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                if (attacker instanceof Player) {
                    PlayerInventory inv = ((Player) attacker).getInventory();
                    if (inv.getHeldItemSlot() > 0) {
                        e.setCancelled(true);
                        attacker.sendMessage(ChatColor.DARK_RED + "You can only attack with the item in your first slot!");
                        //inv.setHeldItemSlot(0);
                    } else {
                        // here we attacc
                        di = CombatManager.playerMeleeAttack((Player) attacker, defender, inv.getItemInMainHand(), plugin);
                        double final_damage = (double) di.damage / 25;
                        e.setDamage(0);
                        le.setHealth(Math.max(le.getHealth() - final_damage, 0));
                        damagedAlready = true;

                        // update health in name
                        CombatManager.updateHealthBar(le, di.damage, true);
                    }
                }
            }

            // update health in name
            CombatManager.updateHealthBar(le, di.damage, damagedAlready);

            // show damage indicator
            CombatManager.showDamageIndicator(le, di.crit, di.damage, plugin);
        }
    }
}
