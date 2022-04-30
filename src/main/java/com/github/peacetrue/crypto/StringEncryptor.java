package com.github.peacetrue.crypto;

import com.github.peacetrue.codec.Codec;

import java.util.Objects;

/**
 * 字符串加解密器。
 *
 * @author peace
 **/
public class StringEncryptor implements Encryptor<String> {

    private final Encryptor<byte[]> encryptor;
    private final Codec outer;
    private final Codec inner;

    /**
     * 加密时：接收 UTF8 字符串明文，并输出 HEX 密文；
     * 解密时：接收 HEX 密文，并输出 UTF8 字符串明文
     *
     * @param encryptor 加解密器
     */
    public StringEncryptor(Encryptor<byte[]> encryptor) {
        this(encryptor, Codec.CHARSET_UTF8, Codec.HEX);
    }

    /**
     * 自定义一个字符串加解密器
     *
     * @param encryptor 字节数组加解密器
     * @param outer     外层加解码器
     * @param inner     内层加解码器
     */
    public StringEncryptor(Encryptor<byte[]> encryptor, Codec outer, Codec inner) {
        this.encryptor = Objects.requireNonNull(encryptor);
        this.outer = Objects.requireNonNull(outer);
        this.inner = Objects.requireNonNull(inner);
    }

    @Override
    public String encrypt(String plaintext) {
        return inner.encode(encryptor.encrypt(outer.decode(plaintext)));
    }

    @Override
    public String decrypt(String ciphertext) {
        return outer.encode(encryptor.decrypt(inner.decode(ciphertext)));
    }
}
