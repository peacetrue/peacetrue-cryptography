package com.github.peacetrue.crypto;

/**
 * 加密器（此处亦指加解密器）。
 *
 * @author peace
 */
public interface Encryptor<T> {

    /**
     * 加密。
     *
     * @param plaintext 明文
     * @return 密文
     */
    T encrypt(T plaintext);

    /**
     * 解密。
     *
     * @param ciphertext 密文
     * @return 明文
     */
    T decrypt(T ciphertext);


}
