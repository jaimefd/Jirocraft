package me.jiroscopio.jirocraftplugin.helpers;

import org.bukkit.ChatColor;

public class StatHelper {

    public static String statToText (String stat_name, Float stat_value, boolean is_player) {
        String final_text;
        if (is_player) final_text = ChatColor.AQUA + "";
        else final_text = ChatColor.AQUA + "+";
        String final_stat;

        if (stat_value == Math.floor(stat_value)) {
            final_stat = String.format("%.0f", stat_value);
        } else {
            final_stat = Double.toString(stat_value);
        }

        switch (stat_name) {
            case "max_health" -> final_text += final_stat + "" + ChatColor.GRAY + " Health";
            case "phys_attack" -> final_text += final_stat + "" + ChatColor.GRAY + " Physical Attack";
            case "magic_attack" -> final_text += final_stat + "" + ChatColor.GRAY + " Magic Attack";
            case "phys_pen" -> final_text += final_stat + "%" + ChatColor.GRAY + " Physical Penetration";
            case "magic_pen" -> final_text += final_stat + "%" + ChatColor.GRAY + " Magic Penetration";
            case "phys_lethality" -> final_text += final_stat + "" + ChatColor.GRAY + " Physical Lethality";
            case "magic_lethality" -> final_text += final_stat + "" + ChatColor.GRAY + " Magic Lethality";
            case "phys_def" -> final_text += final_stat + "" + ChatColor.GRAY + " Physical Defense";
            case "magic_def" -> final_text += final_stat + "" + ChatColor.GRAY + " Magic Defense";
            case "evasion" -> final_text += final_stat + "" + ChatColor.GRAY + " Evasion";
            case "speed" -> final_text += final_stat + "" + ChatColor.GRAY + " Speed";
            default -> final_text += " " + stat_name;
        }
        return final_text;
    }

}
