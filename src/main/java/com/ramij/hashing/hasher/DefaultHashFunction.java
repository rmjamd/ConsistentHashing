package com.ramij.hashing.hasher;

import com.ramij.hashing.HashingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DefaultHashFunction implements HashFunction {

    private static final int LONG_BYTE_COUNT = 8;
    private final MessageDigest md5;

    public DefaultHashFunction() {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new HashingException(e.getMessage());
        }
    }

    @Override
    public long getHash(String input) {
        byte[] hashBytes = computeMD5(input);

        // Convert the first 8 bytes of the hash to a long
        long hashAsLong = 0;
        for (int i = 0; i < LONG_BYTE_COUNT; i++) {
            hashAsLong = (hashAsLong << 8) | (hashBytes[i] & 0xFF);
        }

        return hashAsLong;
    }

    private byte[] computeMD5(String input) {
        return md5.digest(input.getBytes());
    }
}
