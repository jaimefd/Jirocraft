package me.jiroscopio.jirocraftplugin.listeners;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.helpers.ItemHelper;
import me.jiroscopio.jirocraftplugin.guis.MenuManager;
import me.jiroscopio.jirocraftplugin.models.RpgPlayer;
import me.jiroscopio.jirocraftplugin.records.ItemRecord;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerInteractListener implements Listener {

    private final JirocraftPlugin plugin;

    public PlayerInteractListener(JirocraftPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        PlayerInventory inv = e.getPlayer().getInventory();

        if (e.getAction().equals(Action.PHYSICAL)){
            // add functionality when needed
            return;
        }

        if (e.getHand() == null) return;

        ItemStack clickedItem;
        if (e.getHand().equals(EquipmentSlot.HAND)) {
            clickedItem = inv.getItemInMainHand();
        } else if (e.getHand().equals(EquipmentSlot.OFF_HAND)) {
            clickedItem = inv.getItemInOffHand();
        } else {
            System.out.println("How????");
            return;
        }

        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (clickedItem.getType() != Material.AIR) {
                String itemName = ItemHelper.getItemType(plugin, clickedItem);
                if (itemName.contains("HELMET") || itemName.contains("CHESTPLATE") || itemName.contains("LEGGINGS") || itemName.contains("BOOTS"))
                    RpgPlayer.getRpgPlayer(player, plugin).delayedUpdate();

                if (itemName.equals("MAIN_MENU")) {
                    e.setCancelled(true);
                    player.sendMessage(ChatColor.GRAY + "Remember that you can also use /menu instead!");
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5F, 1F);
                    MenuManager.viewMenu(plugin, player);
                } else {
                    // if it doesn't match anything else, check if it's equippable
                    ItemRecord itemRecord = plugin.itemRecords.get(itemName);
                    if (itemRecord != null) {
                        itemRecord.type().equip(player, clickedItem, e.getHand());
                        RpgPlayer.getRpgPlayer(player, plugin).delayedUpdate();
                    }
                }
            }
        }
    }

}
