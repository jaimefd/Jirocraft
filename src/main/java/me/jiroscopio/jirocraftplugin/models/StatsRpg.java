package me.jiroscopio.jirocraftplugin.models;

public class StatsRpg {

    // Elemental stats (Air > Water > Fire > Nature > Earth > Air)
    public float air_power = 0F;
    public float water_power = 0F;
    public float fire_power = 0F;
    public float nature_power = 0F;
    public float earth_power = 0F;
    public float void_power = 0F;

    // Resource stats
    public float max_health = 150F; // Each heart is 50 hp (25 per half). All players have a base of 150 (3 hearts)
    public float health_regen = 0.5F; // Percentage of health recovered per second (heal ticks happen every 4 seconds though)
    public float max_mana = 100F;
    public float mana_regen = 0.5F; // Percentage of mana recovered per second (recover ticks happen every 4 seconds though)

    // Damage stats
    public float attack_power = 0F; // Extra damage for melee hits or arrows - Physical Damage
    public float spell_power = 0F; // Extra damage for magic abilities (spells that usually cost mana) - Magic Damage

    public float crit_chance = 5F;
    public float crit_damage = 50F;
    public float sorcery = 0F; // Turns magic damage done into extra true damage

    public float accuracy = 0F; // Chance for hits to land (0 is 100% chance unless enemy has evasion. Extra accuracy denies evasion)
    public float multihit = 0F; // Chance to hit an extra time (or more if above 100)

    public float attack_speed = 0F; // Extra attack speed multiplier for melee attacks (50 => 150% attack speed)
    public float reload_speed = 0F; // Speed at which ranged weapons recharge (bows) or shoot (guns)
    public float casting_speed = 0F; // Reduced cooldown for spells

    public float phys_pen = 0F; // Percentage of physical defense ignored
    public float magic_pen = 0F; // Percentage of magic defense ignoredd

    public float phys_lethality = 0F; // Flat physical defense ignored
    public float magic_lethality = 0F; // Flat magic defense ignored

    public float knockback_power = 0F;

    // Healing stats
    public float healing_power = 0F; // Multiplies outgoing healing
    public float sustain_power = 0F; // Multiplies incoming or self-healing

    public float life_steal = 0F; // Percentage of damage dealt recovered as HP
    public float melee_drain = 0F; // Like life steal but for specific damage types
    public float ranged_drain = 0F;
    public float spell_drain = 0F;

    // Defense stats
    public float phys_defense = 0F;
    public float magic_defense = 0F;

    public float knockback_resistance = 0F;
    public float evasion = 10F; // Adds a chance to dodge damage. Sneaking increases it for 1 second (the act of sneaking, not staying sneaked all time)
    public float blocking = 0F; // Chance to block hits, which protects from critical damage and halves spell damage. Shielding increases it temporarily

    public float taunting = 0F; // The more you have, the more likely are mobs to target you
    public float absorption = 0F; // Percentage of ally damage absorbed (ally = party members nearby)
    public float reflection = 0F; // Chance to return damage done (you still take it)

    // Other stats
    public float speed = 100F;
    public float haste = 0F;

    public float looting = 0F;
    public float luxury = 0F;
    public float hunting_luck = 0F;

    public float multi_mining = 0F;
    public float mining_luck = 0F;

    public float farming_luck = 0F;

    public float fishing_speed = 0F;
    public float fishing_luck = 0F;
}
