package me.jiroscopio.jirocraftplugin.records;

import java.util.ArrayList;

public record FamilyRecord(String id, String base, int upgrade, ArrayList<String> tiers) {
}
