package me.jiroscopio.jirocraftplugin.listeners;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.enums.Rarity;
import me.jiroscopio.jirocraftplugin.helpers.ItemHelper;
import me.jiroscopio.jirocraftplugin.records.ItemRecord;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

public class PlayerDropItemListener implements Listener {

    private final JirocraftPlugin plugin;

    public PlayerDropItemListener(JirocraftPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        Item drop = e.getItemDrop();
        drop.setGlowing(true);
        String itemName = ItemHelper.getItemType(this.plugin, drop.getItemStack());

        ItemRecord itemRecord = this.plugin.itemRecords.get(itemName);
        if (itemRecord == null) return;

        Scoreboard board = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
        String team_tag = Rarity.values()[itemRecord.rarity()].toString().toLowerCase();
        Team team = board.getTeam(team_tag);
        if (team != null) team.addEntry(drop.getUniqueId().toString());
    }

}
