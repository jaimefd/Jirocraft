package me.jiroscopio.jirocraftplugin.managers;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.helpers.ItemHelper;
import me.jiroscopio.jirocraftplugin.models.RpgEntity;
import me.jiroscopio.jirocraftplugin.models.RpgPlayer;
import me.jiroscopio.jirocraftplugin.records.ItemRecord;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class CombatManager {

    public static class DamageInstance {
        public int damage;
        public boolean crit;
    }

    public static boolean isCritical(Player player)
    {
        return
                player.getFallDistance() > 0.0F &&
                        !player.isOnGround() &&
                        !player.isInsideVehicle() &&
                        !player.hasPotionEffect(PotionEffectType.BLINDNESS) &&
                        player.getLocation().getBlock().getType() != Material.LADDER &&
                        player.getLocation().getBlock().getType() != Material.VINE;
    }

    public static DamageInstance playerMeleeAttack(Player damager, Entity damaged, ItemStack weapon, JirocraftPlugin plugin) {
        Boolean crit = isCritical(damager);
        Random r = new Random();

        RpgPlayer rpgPlayer = RpgPlayer.getRpgPlayer(damager, plugin);
        int weapon_damage = 10;
        if (weapon != null) weapon_damage = getWeaponDamage(weapon, plugin, r);
        return getAttackDamage(weapon_damage, rpgPlayer, damaged, plugin, r, crit);
    }

    public static int getWeaponDamage(ItemStack weapon, JirocraftPlugin plugin, Random r) {
        int damage = 10;

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

    public static DamageInstance getAttackDamage(int damage, RpgPlayer attacker, Entity defender, JirocraftPlugin plugin, Random r, Boolean crit) {
        DamageInstance di = new DamageInstance();
        int final_damage = damage;
        float physical_power = attacker.getStats().getOrDefault("physical_power", 0F);
        float crit_chance = attacker.getStats().getOrDefault("crit_chance", 0F);
        float crit_damage = attacker.getStats().getOrDefault("crit_damage", 0F);

        float physical_pen = attacker.getStats().getOrDefault("physical_pen", 0F);
        float physical_lethality = attacker.getStats().getOrDefault("phys_lethality", 0F);

        // Critical attacks in Minecraft (attacking while falling) increase your crit chance by 30
        if (crit) {
            crit_chance += 40f;
        }

        // Convert extra crit chance (above 100) into crit damage, at 50% ratio
        if (crit_chance > 100) {
            crit_damage += (crit_chance - 100) / 2;
            crit_chance = 100;
        }

        float attack_multiplier = 1 + physical_power/100;

        // does it crit?
        if (r.nextInt(100) < crit_chance) {
            attack_multiplier += crit_damage/100;
            di.crit = true;
        }

        // Get the final damage DEALT
        final_damage = Math.round(final_damage * attack_multiplier);

        // Now check for defenses
        float physical_defense = 0F;

        if (defender instanceof Player p) {
            RpgPlayer defenderRpg = RpgPlayer.getRpgPlayer(p, plugin);
            physical_defense = defenderRpg.getStats().getOrDefault("physical_defense", 0F);
        } else if (defender instanceof LivingEntity le) {
            RpgEntity defenderRpg = RpgEntity.getRpgEntity(le, plugin);
            physical_defense = defenderRpg.getStats().getOrDefault("physical_defense", 0F);
        }

        float final_defense;
        if (physical_defense >= 0F) final_defense = (physical_defense * (1 - physical_pen)) - physical_lethality;
        else final_defense = (physical_defense - physical_lethality) * (1 + physical_pen);

        if (final_defense >= 0F) final_damage = Math.round(final_damage * (100 / (100 + final_defense)));
        else final_damage = Math.round(final_damage * (2 - (100 / (100 - final_defense))));

        di.damage = final_damage;

        return di;
    }

}
