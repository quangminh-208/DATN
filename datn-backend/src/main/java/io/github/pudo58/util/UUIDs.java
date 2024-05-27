package io.github.pudo58.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDs {
    public static byte[] uuidToBinary(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }
}
