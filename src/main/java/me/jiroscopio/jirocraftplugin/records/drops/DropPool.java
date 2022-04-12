package me.jiroscopio.jirocraftplugin.records.drops;

import me.jiroscopio.jirocraftplugin.helpers.NumberGenerator;

import java.util.ArrayList;

public record DropPool(double chance, boolean tool, int tool_power_min, int tool_power_max, NumberGenerator rolls, double luck_multiplier,
                       double looting_multiplier, ArrayList<DropEntry> entries, DropEntry single_entry, int total_weight) {
}
