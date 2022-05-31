package me.jiroscopio.jirocraftplugin.records;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.enums.ItemType;
import me.jiroscopio.jirocraftplugin.helpers.ItemHelper;
import me.jiroscopio.jirocraftplugin.helpers.StatHelper;
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
import java.util.Map;
import java.util.UUID;

public record ItemRecord(String id, String name, Material base_material, int value, int currency, int rarity, int modelData,
                         ArrayList<String> lore, ArrayList<String> tags, String family, ItemType type, int tool_power,
                         String head_owner, String head_value, Map<String,Float> base_stats, int damage, int damage_variance) {

    public ItemStack getItemStack(ItemStack source, JirocraftPlugin plugin) {
        ItemStack itemStack = source;
        if (source == null) itemStack = new ItemStack(base_material);

        // if head, get the skin
        if (base_material.equals(Material.PLAYER_HEAD) && head_owner != null && head_value != null) {
            itemStack = ItemHelper.getCustomHead(head_owner, head_value);
        }

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
        itemMeta.setUnbreakable(true);

        List<String> itemLore = new ArrayList<>();

        if (modelData > 0) {
            itemMeta.setCustomModelData(modelData);
        }

        // hide attributes, since we will use our custom ones
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

        // if glow is true, we want the item to have the enchantment glowing effect
        if (tags.contains("glow")) itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        if (!lore.isEmpty()) {
            for (String line : lore)
                itemLore.add(ChatColor.GRAY + line);
        }

        if (damage != 5) {
            itemLore.add(ChatColor.AQUA + "" + damage + ChatColor.GRAY + " Damage");
        }

        if (base_stats != null) {
            for (String stat_name : base_stats.keySet()) {
                itemLore.add(StatHelper.statToText(stat_name, base_stats.get(stat_name), false));
            }
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

        ChatColor rarityColor;

        switch (rarity) {
            case 0 -> {
                rarityColor = ChatColor.WHITE;
                itemLore.add(ChatColor.DARK_GRAY + "Rarity: " + ChatColor.WHITE + "Basic");
            }
            case 1 -> {
                rarityColor = ChatColor.GREEN;
                itemLore.add(ChatColor.DARK_GRAY + "Rarity: " + ChatColor.GREEN + "Common");
            }
            case 2 -> {
                rarityColor = ChatColor.DARK_GREEN;
                itemLore.add(ChatColor.DARK_GRAY + "Rarity: " + ChatColor.DARK_GREEN + "Uncommon");
            }
            case 3 -> {
                rarityColor = ChatColor.BLUE;
                itemLore.add(ChatColor.DARK_GRAY + "Rarity: " + ChatColor.BLUE + "Rare");
            }
            case 4 -> {
                rarityColor = ChatColor.DARK_BLUE;
                itemLore.add(ChatColor.DARK_GRAY + "Rarity: " + ChatColor.DARK_BLUE + "Very Rare");
            }
            case 5 -> {
                rarityColor = ChatColor.DARK_PURPLE;
                itemLore.add(ChatColor.DARK_GRAY + "Rarity: " + ChatColor.DARK_PURPLE + "Epic");
            }
            case 6 -> {
                rarityColor = ChatColor.GOLD;
                itemLore.add(ChatColor.DARK_GRAY + "Rarity: " + ChatColor.GOLD + "Legendary");
            }
            case 7 -> {
                rarityColor = ChatColor.DARK_RED;
                itemLore.add(ChatColor.DARK_GRAY + "Rarity: " + ChatColor.DARK_RED + "Mythic");
            }
            default -> rarityColor = ChatColor.WHITE;
        }

        if (name != null) itemMeta.setDisplayName(rarityColor + name);
        else {
            if (itemMeta.hasDisplayName()) {
                itemMeta.setDisplayName(rarityColor + itemMeta.getDisplayName());
            } else if (itemMeta.hasLocalizedName()) {
                itemMeta.setDisplayName(rarityColor + itemMeta.getLocalizedName());
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
