package me.jiroscopio.jirocraftplugin.listeners;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.models.RpgPlayer;
import me.jiroscopio.jirocraftplugin.records.ItemRecord;
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

        RpgPlayer.registerRpgPlayer(p, this.plugin);

        if (!p.hasPlayedBefore()) {
            ItemRecord main_menu = plugin.itemRecords.get("MAIN_MENU");
            if (main_menu != null)
                p.getInventory().addItem(main_menu.getItemStack(null, plugin));
        }
    }

}
