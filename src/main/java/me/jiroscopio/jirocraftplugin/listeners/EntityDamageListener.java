package me.jiroscopio.jirocraftplugin.listeners;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.managers.CombatManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashSet;
import java.util.Set;

public class EntityDamageListener implements Listener {

    private final JirocraftPlugin plugin;

    public EntityDamageListener(JirocraftPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageEvent e) {
        Entity defender = e.getEntity();
        Set<EntityDamageEvent.DamageCause> foo = new HashSet<>();
        foo.add(EntityDamageEvent.DamageCause.ENTITY_ATTACK);
        foo.add(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION);
        foo.add(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK);
        foo.add(EntityDamageEvent.DamageCause.DRAGON_BREATH);
        foo.add(EntityDamageEvent.DamageCause.MAGIC);
        foo.add(EntityDamageEvent.DamageCause.PROJECTILE);
        foo.add(EntityDamageEvent.DamageCause.SONIC_BOOM);
        foo.add(EntityDamageEvent.DamageCause.THORNS);

        if (foo.contains(e.getCause())) return;

        if (defender instanceof LivingEntity le) {
            int damage = (int) Math.round(e.getDamage() * 25);

            // update health in name
            CombatManager.updateHealthBar(le, damage, false);

            // show damage indicator - for now we make it show only in entity vs entity combat
            // CombatManager.showDamageIndicator(le, false, damage, plugin);
        }
    }

}
