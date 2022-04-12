package me.jiroscopio.jirocraftplugin.files;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.records.FacingRecord;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import java.util.HashMap;

public class FacingManager extends FileManager{

    public FacingManager(JirocraftPlugin plugin, String fileName) {
        super(plugin, fileName);
    }

    public void setupManager() {
        Material baseMaterial;
        HashMap<BlockFace, String> faces = new HashMap<>();

        for (String key : this.getConfig().getKeys(false)) {
            baseMaterial = Material.getMaterial(key);
            if (baseMaterial == null) continue;
            faces.clear();

            for (BlockFace face : BlockFace.values()) {
                String stringFace = key + "." + face.toString().toLowerCase();
                if (this.getConfig().contains(stringFace))
                    faces.put(face, this.getConfig().getString(stringFace));
            }

            FacingRecord facing = new FacingRecord(baseMaterial, faces);

            this.plugin.facingRecords.put(baseMaterial, facing);
        }

        System.out.println("FACES LOADED");
    }
}
