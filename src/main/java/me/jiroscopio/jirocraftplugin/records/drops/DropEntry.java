package me.jiroscopio.jirocraftplugin.records.drops;

import me.jiroscopio.jirocraftplugin.helpers.NumberGenerator;

public record DropEntry(String drop, String type, int announce, NumberGenerator amount, int weight, boolean tiered) {
}
