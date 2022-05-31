package me.jiroscopio.jirocraftplugin.enums;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public enum ItemType {
    ANY,
    MATERIAL,
    PICKAXE,
    SHOVEL,
    HOE,
    AXE,
    SWORD,
    DAGGER,
    SPEAR,
    BOW,
    QUIVER,
    CROSSBOW,
    AUTO_CROSSBOW,
    TRIDENT,
    WINGS,
    WAND, // healing
    STAFF, // magic damage
    GLOVES,
    SHIELD,
    ORB, // afk aoe damage
    HELMET,
    CHESTPLATE,
    LEGGINGS,
    BOOTS;

    public boolean isArmor () {
        return switch (this) {
            case HELMET, CHESTPLATE, LEGGINGS, BOOTS -> true;
            default -> false;
        };
    }

    public void equip (Player p, ItemStack stack, EquipmentSlot slot) {
        PlayerInventory inv = p.getInventory();
        if (isArmor()) {
            switch (this) {
                case HELMET:
                    if(inv.getHelmet() == null) {
                        inv.setHelmet(stack);
                        inv.setItem(slot, new ItemStack(Material.AIR));
                        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 1,1);
                    }
                    break;
                case CHESTPLATE:
                    if(inv.getChestplate() == null) {
                        inv.setChestplate(stack);
                        inv.setItem(slot, new ItemStack(Material.AIR));
                        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 1,1);
                    }
                    break;
                case LEGGINGS:
                    if(inv.getLeggings() == null) {
                        inv.setLeggings(stack);
                        inv.setItem(slot, new ItemStack(Material.AIR));
                        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 1,1);
                    }
                    break;
                case BOOTS:
                    if(inv.getBoots() == null) {
                        inv.setBoots(stack);
                        inv.setItem(slot, new ItemStack(Material.AIR));
                        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 1,1);
                    }
                    break;
            }
        }
    }
}
