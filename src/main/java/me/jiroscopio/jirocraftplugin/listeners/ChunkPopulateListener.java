package me.jiroscopio.jirocraftplugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;

public class ChunkPopulateListener implements Listener {

    @EventHandler
    public void onChunkPopulate(ChunkPopulateEvent e) {
        System.out.println("CHUNK POPULATE EVENT NAME: " + e.getEventName());
        System.out.println("CHUNK POPULATE EVENT STRING: " + e);
    }

}
