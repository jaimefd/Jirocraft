package me.jiroscopio.jirocraftplugin.listeners;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class PickupItemListener implements Listener {

    private final JirocraftPlugin plugin;

    public PickupItemListener(JirocraftPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e) {
        Item picked = e.getItem();
        ItemStack pickedStack = picked.getItemStack();

        if (pickedStack.getItemMeta() == null) return;

        PersistentDataContainer container = pickedStack.getItemMeta().getPersistentDataContainer();
        NamespacedKey item_type_key = new NamespacedKey(plugin, "item-type");

        String item_type = pickedStack.getType().toString();
        if (container.has(item_type_key, PersistentDataType.STRING)) {
            item_type = container.get(item_type_key, PersistentDataType.STRING);
        }
        picked.setItemStack(Objects.requireNonNull(plugin.itemRecords.get(item_type).getItemStack(pickedStack, plugin)));
    }

}
