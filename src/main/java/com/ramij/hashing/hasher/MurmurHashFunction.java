package com.ramij.hashing.hasher;

import java.nio.ByteBuffer;

public class MurmurHashFunction implements HashFunction {
    @Override
    public long getHash(String key) {
        ByteBuffer data = ByteBuffer.wrap(key.getBytes());
        int seed = 0x9747b28c; // Default seed value for MurmurHash
        int m = 0x5bd1e995;
        int r = 24;

        int h = seed ^ data.remaining();

        while (data.remaining() >= 4) {
            int k = data.getInt();

            k *= m;
            k ^= k >>> r;
            k *= m;

            h *= m;
            h ^= k;
        }

        if (data.remaining() > 0) {
            ByteBuffer tail = ByteBuffer.allocate(4).put(new byte[]{0, 0, 0, data.get()});
            tail.rewind();

            int k = tail.getInt();

            k *= m;
            k ^= k >>> r;
            k *= m;

            h ^= k;
        }

        h ^= h >>> 13;
        h *= m;
        h ^= h >>> 15;

        return Integer.toUnsignedLong(h);
    }
}
