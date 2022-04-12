package me.jiroscopio.jirocraftplugin.helpers;

import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public interface TagTypes {

    PersistentDataType<byte[], java.util.UUID> UUID = new UUIDTagType();

}
