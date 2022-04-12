package me.jiroscopio.jirocraftplugin.records;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.enums.ToolType;
import me.jiroscopio.jirocraftplugin.helpers.TagTypes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record ItemRecord(String id, String name, Material base_material, int value, int currency, int rarity, int modelData,
                         String lore, ArrayList<String> tags, String family, ToolType tool_type, int tool_power) {

    public ItemStack getItemStack(ItemStack source, JirocraftPlugin plugin) {
        ItemStack itemStack = source;
        if (source == null) itemStack = new ItemStack(base_material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return null;

        // To keep track of custom items even when renamed, we store the name id of that item
        NamespacedKey item_type_key = new NamespacedKey(plugin, "item-type");
        itemMeta.getPersistentDataContainer().set(item_type_key, PersistentDataType.STRING, id);

        NamespacedKey item_uuid_key = new NamespacedKey(plugin, "item-uuid");
        if (tags.contains("unique")) {
            if (!itemMeta.getPersistentDataContainer().has(item_uuid_key, TagTypes.UUID)) {
                UUID item_uuid = UUID.randomUUID();
                itemMeta.getPersistentDataContainer().set(item_uuid_key, TagTypes.UUID, item_uuid);
            }
        }

        // Give items the hide unbreakable flag so we can check if they are custom items later
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        List<String> itemLore = new ArrayList<>();

        if (modelData > 0) {
            itemMeta.setCustomModelData(modelData);
        }

        // if glow is true, we want the item to have the enchantment glowing effect
        if (tags.contains("glow")) itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        if (lore != null) {
            itemLore.add(ChatColor.DARK_GRAY + lore);
        }

        if (value > 0) {
            switch (currency) {
                case 1 -> // silver
                        itemLore.add(ChatColor.DARK_GRAY + "Price: " + ChatColor.GRAY + value + ChatColor.WHITE + ChatColor.BOLD + " S");
                case 2 -> // gold
                        itemLore.add(ChatColor.DARK_GRAY + "Price: " + ChatColor.GRAY + value + ChatColor.YELLOW + ChatColor.BOLD + " G");
                default -> // bronze, which is also 0
                        itemLore.add(ChatColor.DARK_GRAY + "Price: " + ChatColor.GRAY + value + ChatColor.DARK_RED + ChatColor.BOLD + " B");
            }
        }

        switch (rarity) {
            case 1 -> {
                if (name != null) itemMeta.setDisplayName(ChatColor.GREEN + name);
                itemLore.add(ChatColor.DARK_GRAY + "Rarity: " + ChatColor.GREEN + "Common");
            }
            case 2 -> {
                if (name != null) itemMeta.setDisplayName(ChatColor.DARK_GREEN + name);
                itemLore.add(ChatColor.DARK_GRAY + "Rarity: " + ChatColor.DARK_GREEN + "Uncommon");
            }
            case 3 -> {
                if (name != null) itemMeta.setDisplayName(ChatColor.BLUE + name);
                itemLore.add(ChatColor.DARK_GRAY + "Rarity: " + ChatColor.BLUE + "Rare");
            }
            case 4 -> {
                if (name != null) itemMeta.setDisplayName(ChatColor.DARK_BLUE + name);
                itemLore.add(ChatColor.DARK_GRAY + "Rarity: " + ChatColor.DARK_BLUE + "Very Rare");
            }
            case 5 -> {
                if (name != null) itemMeta.setDisplayName(ChatColor.DARK_PURPLE + name);
                itemLore.add(ChatColor.DARK_GRAY + "Rarity: " + ChatColor.DARK_PURPLE + "Epic");
            }
            case 6 -> {
                if (name != null) itemMeta.setDisplayName(ChatColor.GOLD + name);
                itemLore.add(ChatColor.DARK_GRAY + "Rarity: " + ChatColor.GOLD + "Legendary");
            }
            case 7 -> {
                if (name != null) itemMeta.setDisplayName(ChatColor.DARK_RED + name);
                itemLore.add(ChatColor.DARK_GRAY + "Rarity: " + ChatColor.DARK_RED + "Mythic");
            }
            default -> {
                if (name != null) itemMeta.setDisplayName(ChatColor.WHITE + name);
                itemLore.add(ChatColor.DARK_GRAY + "Rarity: " + ChatColor.WHITE + "Basic");
            }
        }

        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);

        if (tags.contains("glow") && itemStack.getType() != Material.BOW) {
            itemStack.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        }

        return itemStack;
    }

}
