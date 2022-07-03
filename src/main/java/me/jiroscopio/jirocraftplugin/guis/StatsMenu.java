package me.jiroscopio.jirocraftplugin.guis;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.helpers.StatHelper;
import me.jiroscopio.jirocraftplugin.models.RpgPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatsMenu {

    public static void statsPreview (ItemStack statViewer, Player player, JirocraftPlugin plugin) {
        SkullMeta headMeta = (SkullMeta) statViewer.getItemMeta();
        if (headMeta == null) return;
        headMeta.setDisplayName("§bView Stats");
        headMeta.setOwningPlayer(player);
        List<String> lore = new ArrayList<>();

        lore.add("§aClick to view your stats!");
        lore.add("");

        RpgPlayer rpgPlayer = RpgPlayer.getRpgPlayer(player, plugin);
        Map<String,Float> playerStats = rpgPlayer.getStats();
        for (String stat : playerStats.keySet())
            lore.add(StatHelper.statToText(stat, playerStats.get(stat), true));

        headMeta.setLore(lore);
        statViewer.setItemMeta(headMeta);
    }

}
