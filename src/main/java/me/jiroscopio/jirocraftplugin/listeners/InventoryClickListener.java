package me.jiroscopio.jirocraftplugin.listeners;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.enums.ItemType;
import me.jiroscopio.jirocraftplugin.guis.MenuManager;
import me.jiroscopio.jirocraftplugin.helpers.ItemHelper;
import me.jiroscopio.jirocraftplugin.models.PlayerRpg;
import me.jiroscopio.jirocraftplugin.records.ItemRecord;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryClickListener implements Listener {

    private final JirocraftPlugin plugin;

    public InventoryClickListener(JirocraftPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        String itemName = ItemHelper.getItemType(plugin, e.getCurrentItem());
        Player player = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null)
            return;

        if (itemName.equals("MAIN_MENU") && e.getClick().isRightClick()) {
            e.setCancelled(true);

            player.sendMessage(ChatColor.GRAY + "Remember that you can also use /menu instead!");
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5F, 1F);
            MenuManager.viewMenu(plugin, player);
            return;
        }

        if (e.getSlotType().equals(InventoryType.SlotType.ARMOR)) {
            PlayerRpg.getRpgPlayer(player, plugin).delayedUpdate();
        } else if (e.getClick().isShiftClick()) {
            if (itemName.contains("HELMET") || itemName.contains("CHESTPLATE") || itemName.contains("LEGGINGS") || itemName.contains("BOOTS")) {
                PlayerRpg.getRpgPlayer(player, plugin).delayedUpdate();
            } else {
                ItemRecord itemRecord = plugin.itemRecords.get(itemName);
                if (itemRecord != null) {
                    if (itemRecord.type().isArmor()) PlayerRpg.getRpgPlayer(player, plugin).delayedUpdate();
                }
            }
        }
    }

}
