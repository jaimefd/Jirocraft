package me.jiroscopio.jirocraftplugin.helpers;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.records.ItemRecord;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ItemHelper {

    public static String getItemType(JirocraftPlugin plugin, ItemStack stack) {
        String item_id = stack.getType().toString();
        ItemMeta item_meta = stack.getItemMeta();
        if (item_meta == null) return item_id;

        PersistentDataContainer container = item_meta.getPersistentDataContainer();

        NamespacedKey item_type_key = new NamespacedKey(plugin, "item-type");
        if (container.has(item_type_key, PersistentDataType.STRING))
            return container.get(item_type_key, PersistentDataType.STRING);
        else return item_id;
    }

    public static ItemStack getItemByName(JirocraftPlugin plugin, String item_name) {
        ItemRecord itemRecord = plugin.itemRecords.get(item_name.toUpperCase());
        if (itemRecord == null) return null;
        else return itemRecord.getItemStack(null, plugin);
    }

    public static boolean containsCustomItem(JirocraftPlugin plugin, Inventory inv, String item_id) {
        ItemRecord base_item = plugin.itemRecords.get(item_id);
        for (ItemStack item : inv) {
            if (item.getType().equals(base_item.base_material()))
                if(getItemType(plugin, item).equals(item_id)) return true;
        }
        return false;
    }

    public static ItemStack getBackgroundItem() {
        ItemStack backgroundItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta backgroundMeta = backgroundItem.getItemMeta();
        if (backgroundMeta == null) return backgroundItem;
        backgroundMeta.setDisplayName(ChatColor.RESET + "");
        backgroundItem.setItemMeta(backgroundMeta);
        return backgroundItem;
    }

    public static ItemStack getCustomHead(String owner_id, String value) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        if (value.isEmpty()) return skull;
        return Bukkit.getUnsafe().modifyItemStack(skull,
                "{SkullOwner:{Id:[" + owner_id + "],Properties:{textures:[{Value:\"" + value + "\"}]}}}"
        );
    }

    public static boolean isArmor(String item_id, JirocraftPlugin plugin) {
        if (item_id.contains("HELMET") || item_id.contains("CHESTPLATE") || item_id.contains("LEGGINGS") || item_id.contains("BOOTS")) return true;
        ItemRecord itemRecord = plugin.itemRecords.get(item_id);
        if (itemRecord == null) return false;
        if (itemRecord.type() == null) return false;
        return itemRecord.type().isArmor();
    }
}
