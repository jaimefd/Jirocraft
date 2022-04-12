package me.jiroscopio.jirocraftplugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    final String FILE_ID = "1Yycs-ofCdCXvTIM1yR-gXQ6eKs7nDOUr";

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().setResourcePack("https://drive.google.com/uc?export=download&id=" + FILE_ID);
    }

}
