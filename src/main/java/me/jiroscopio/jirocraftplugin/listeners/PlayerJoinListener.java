package me.jiroscopio.jirocraftplugin.listeners;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.helpers.ItemHelper;
import me.jiroscopio.jirocraftplugin.records.ItemRecord;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final JirocraftPlugin plugin;

    public PlayerJoinListener(JirocraftPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String FILE_ID = "1Yycs-ofCdCXvTIM1yR-gXQ6eKs7nDOUr";
        p.setResourcePack("https://drive.google.com/uc?export=download&id=" + FILE_ID);

        if (!p.hasPlayedBefore()) {
            ItemRecord profile_viewer = plugin.itemRecords.get("PROFILE_VIEWER");
            if (profile_viewer != null)
                p.getInventory().addItem(profile_viewer.getItemStack(null, plugin));
        }
    }

}
