package com.github.peacetrue.encoder;

import java.util.Base64;

/**
 * Base64编码解码器
 *
 * @author xiayx
 */
public class Base64EncoderDecoder implements EncoderDecoder {

    public static final Base64EncoderDecoder DEFAULT = new Base64EncoderDecoder();

    private Base64EncoderDecoder() {
    }

    @Override
    public String encode(byte[] plaintext) {
        return Base64.getEncoder().encodeToString(plaintext);
    }

    @Override
    public byte[] decode(String ciphertext) {
        return Base64.getDecoder().decode(ciphertext);
    }
}
