package me.jiroscopio.jirocraftplugin.helpers;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDTagType implements PersistentDataType<byte[], UUID> {

    public @NonNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    public @NonNull Class<UUID> getComplexType() {
        return UUID.class;
    }

    public byte @NonNull [] toPrimitive(UUID complex, @NonNull PersistentDataAdapterContext context) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(complex.getMostSignificantBits());
        bb.putLong(complex.getLeastSignificantBits());
        return bb.array();
    }

    public @NonNull UUID fromPrimitive(byte @NonNull [] primitive, @NonNull PersistentDataAdapterContext context) {
        ByteBuffer bb = ByteBuffer.wrap(primitive);
        long firstLong = bb.getLong();
        long secondLong = bb.getLong();
        return new UUID(firstLong, secondLong);
    }
}
