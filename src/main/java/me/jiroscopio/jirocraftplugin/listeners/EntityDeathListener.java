package me.jiroscopio.jirocraftplugin.listeners;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {

    private final JirocraftPlugin plugin;

    public EntityDeathListener(JirocraftPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        LivingEntity le = e.getEntity();

        if (!le.getPassengers().isEmpty()) {
            /*for (Entity pass : le.getPassengers()) {
                String passName = pass.getCustomName();
                if (passName != null) {
                    if (passName.endsWith("❤")) pass.remove();
                }
            }*/
        }
    }
}
