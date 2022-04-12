package me.jiroscopio.jirocraftplugin.helpers;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.records.ItemRecord;
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

    public static boolean containsCustomItem(JirocraftPlugin plugin, Inventory inv, String item_id) {
        ItemRecord base_item = plugin.itemRecords.get(item_id);
        for (ItemStack item : inv) {
            if (item.getType().equals(base_item.base_material()))
                if(getItemType(plugin, item).equals(item_id)) return true;
        }
        return false;
    }
}
