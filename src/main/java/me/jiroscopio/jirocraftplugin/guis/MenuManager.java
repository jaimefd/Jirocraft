package me.jiroscopio.jirocraftplugin.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.helpers.ItemHelper;
import me.jiroscopio.jirocraftplugin.records.ItemRecord;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class MenuManager {

    public static void viewMenu(JirocraftPlugin plugin, Player player) {
        ChestGui mainView = new ChestGui(6, "Main Menu");
        mainView.setOnGlobalClick(event -> event.setCancelled(true));

        StaticPane pane = new StaticPane(0, 0, 9, 6);
        pane.fillWith(ItemHelper.getBackgroundItem());

        ItemStack masteryMenu = ItemHelper.getItemByName(plugin, "MASTERY_MENU");
        if (masteryMenu != null) {
            pane.addItem(new GuiItem(masteryMenu, event -> {
                Player clicker = (Player) event.getWhoClicked();
                clicker.sendMessage("These are your masteries!");
                clicker.playSound(clicker.getLocation(), Sound.UI_BUTTON_CLICK, 0.5F, 1F);
            }), 3, 1);
        }

        ItemStack styleMenu = ItemHelper.getItemByName(plugin, "STYLE_MENU");
        if (styleMenu != null) {
            pane.addItem(new GuiItem(styleMenu, event -> {
                Player clicker = (Player) event.getWhoClicked();
                clicker.sendMessage("These are your styles!");
                clicker.playSound(clicker.getLocation(), Sound.UI_BUTTON_CLICK, 0.5F, 1F);
            }), 5, 1);
        }

        ItemStack basesMenu = ItemHelper.getItemByName(plugin, "BASES_MENU");
        if (basesMenu != null) {
            pane.addItem(new GuiItem(basesMenu, event -> {
                Player clicker = (Player) event.getWhoClicked();
                clicker.sendMessage("These are your bases!");
                clicker.playSound(clicker.getLocation(), Sound.UI_BUTTON_CLICK, 0.5F, 1F);
            }), 1, 2);
        }

        ItemStack locationsMenu = ItemHelper.getItemByName(plugin, "LOCATIONS_MENU");
        if (locationsMenu != null) {
            pane.addItem(new GuiItem(locationsMenu), 1, 3);
        }

        // Create the skull with player skin
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        StatsMenu.statsPreview(playerHead, player, plugin);

        pane.addItem(new GuiItem(playerHead, event -> {
            Player clicker = (Player) event.getWhoClicked();
            clicker.sendMessage("These are your stats!");
            clicker.playSound(clicker.getLocation(), Sound.UI_BUTTON_CLICK, 0.5F, 1F);
        }), 4, 2);

        ItemStack equipmentMenu = ItemHelper.getItemByName(plugin, "EQUIPMENT_MENU");
        if (equipmentMenu != null) {
            pane.addItem(new GuiItem(equipmentMenu, event -> {
                Player clicker = (Player) event.getWhoClicked();
                clicker.sendMessage("This is your equipment!");
                clicker.playSound(clicker.getLocation(), Sound.UI_BUTTON_CLICK, 0.5F, 1F);
            }), 4, 3);
        }

        ItemStack friendsMenu = ItemHelper.getItemByName(plugin, "FRIENDS_MENU");
        if (friendsMenu != null) {
            pane.addItem(new GuiItem(friendsMenu, event -> {
                Player clicker = (Player) event.getWhoClicked();
                clicker.sendMessage("These are your friends!");
                clicker.playSound(clicker.getLocation(), Sound.UI_BUTTON_CLICK, 0.5F, 1F);
            }), 7, 2);
        }

        ItemStack guildsMenu = ItemHelper.getItemByName(plugin, "GUILD_MENU");
        if (guildsMenu != null) {
            pane.addItem(new GuiItem(guildsMenu, event -> {
                Player clicker = (Player) event.getWhoClicked();
                clicker.sendMessage("Are you in a guild?");
                clicker.playSound(clicker.getLocation(), Sound.UI_BUTTON_CLICK, 0.5F, 1F);
            }), 7, 3);
        }

        ItemStack mailMenu = ItemHelper.getItemByName(plugin, "MAIL_MENU");
        if (mailMenu != null) {
            pane.addItem(new GuiItem(mailMenu, event -> {
                Player clicker = (Player) event.getWhoClicked();
                clicker.sendMessage("These are your messages!");
                clicker.playSound(clicker.getLocation(), Sound.UI_BUTTON_CLICK, 0.5F, 1F);
            }), 3, 4);
        }

        ItemStack backpackMenu = ItemHelper.getItemByName(plugin, "BACKPACK_MENU");
        if (backpackMenu != null) {
            pane.addItem(new GuiItem(backpackMenu, event -> {
                Player clicker = (Player) event.getWhoClicked();
                clicker.sendMessage("This is your backpack!");
                clicker.playSound(clicker.getLocation(), Sound.UI_BUTTON_CLICK, 0.5F, 1F);
            }), 4, 4);
        }

        ItemStack mineDexMenu = ItemHelper.getItemByName(plugin, "MINEDEX_MENU");
        if (mineDexMenu != null) {
            pane.addItem(new GuiItem(mineDexMenu, event -> {
                Player clicker = (Player) event.getWhoClicked();
                clicker.sendMessage("This is your MineDex!");
                clicker.playSound(clicker.getLocation(), Sound.UI_BUTTON_CLICK, 0.5F, 1F);
            }), 5, 4);
        }

        ItemStack settingsMenu = ItemHelper.getItemByName(plugin, "SETTINGS_MENU");
        if (settingsMenu != null) {
            pane.addItem(new GuiItem(settingsMenu, event -> {
                Player clicker = (Player) event.getWhoClicked();
                clicker.sendMessage("This is your settings!");
                clicker.playSound(clicker.getLocation(), Sound.UI_BUTTON_CLICK, 0.5F, 1F);
            }), 4, 5);
        }

        ItemStack convertMenus = ItemHelper.getItemByName(plugin, "CONVERT_MENUS");
        if (convertMenus != null) {
            pane.addItem(new GuiItem(convertMenus, event -> {
                Player clicker = (Player) event.getWhoClicked();
                clicker.sendMessage("Converting menus? Maybe?");
                clicker.playSound(clicker.getLocation(), Sound.UI_BUTTON_CLICK, 0.5F, 1F);
            }), 8, 5);
        }

        mainView.addPane(pane);

        mainView.show(player);
    }

}
