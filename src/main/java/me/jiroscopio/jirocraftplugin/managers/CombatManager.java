package me.jiroscopio.jirocraftplugin.managers;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.enums.DamageType;
import me.jiroscopio.jirocraftplugin.helpers.ItemHelper;
import me.jiroscopio.jirocraftplugin.models.PlayerRpg;
import me.jiroscopio.jirocraftplugin.records.ItemRecord;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class CombatManager {

    public static void meleeAttack(Entity damager, Entity damaged, ItemStack weapon, ClickType click, JirocraftPlugin plugin, String[] tags) {
        if (click.isLeftClick()) {
            Random r = new Random();
            if (damager instanceof Player p) {
                PlayerRpg playerRpg = PlayerRpg.getRpgPlayer(p, plugin);
                int weapon_damage = 5;
                if (weapon != null) weapon_damage = getWeaponDamage(weapon, plugin, r);
                int damage = getAttackDamage(weapon_damage, playerRpg, plugin, r);
            }
        }
    }

    public static int getWeaponDamage(ItemStack weapon, JirocraftPlugin plugin, Random r) {
        int damage = 5;

        String name = ItemHelper.getItemType(plugin, weapon);
        ItemRecord itemRecord = plugin.itemRecords.get(name.toUpperCase());
        if (itemRecord != null) {
            damage = itemRecord.damage();
            if (itemRecord.damage_variance() > 0) {
                damage += r.nextInt(itemRecord.damage_variance() + 1);
            }
        }

        return damage;
    }

    public static int getAttackDamage(int damage, PlayerRpg attacker, JirocraftPlugin plugin, Random r, String... tags) {
        int final_damage = damage;

        final_damage += attacker.getStats().get("attack_power");

        // does it crit?
        if (r.nextInt(100) < attacker.getStats().get("crit_chance")) {
            final_damage = Math.round(final_damage * attacker.getStats().get("crit_damage"));
        }


        return final_damage;
    }

}
