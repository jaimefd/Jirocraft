package me.jiroscopio.jirocraftplugin.records;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import java.util.HashMap;

public record FacingRecord(Material baseMaterial, HashMap<BlockFace, String> faces) {
}
