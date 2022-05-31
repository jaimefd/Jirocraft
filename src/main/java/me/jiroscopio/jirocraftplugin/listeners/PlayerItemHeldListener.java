package me.jiroscopio.jirocraftplugin.listeners;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.models.PlayerRpg;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerItemHeldListener implements Listener {

    private final JirocraftPlugin plugin;

    public PlayerItemHeldListener(JirocraftPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent e) {
        PlayerRpg.getRpgPlayer(e.getPlayer(), plugin).delayedUpdate();
    }
}
