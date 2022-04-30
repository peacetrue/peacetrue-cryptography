package com.github.peacetrue.codec;

import java.util.Base64;

/**
 * Base64 编解码器。
 *
 * @author peace
 */
public class Base64Codec implements Codec {

    public static final Base64Codec DEFAULT = new Base64Codec();

    private Base64Codec() {
    }

    @Override
    public String encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    @Override
    public byte[] decode(String string) {
        return Base64.getDecoder().decode(string);
    }
}
