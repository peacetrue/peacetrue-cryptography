package com.github.peacetrue.encryption;

import java.util.Arrays;

/**
 * 加密服务
 *
 * @author xiayx
 * @see <a href="https://blog.csdn.net/fanxiaobin577328725/article/details/51713624">java之jce</a>
 */
public interface EncryptService {

    /**
     * 将 {@code plaintext} 明文字节数组加密成密文
     *
     * @param plaintext 想要加密的明文字节数组
     * @return 加密后的密文字节数组
     */
    byte[] encrypt(byte[] plaintext);

    /**
     * 验证一个密文是否匹配
     *
     * @param plaintext  想要加密的明文字节数组
     * @param ciphertext 加密后的密文字节数组
     * @return true，如果有效；否则 false
     */
    default boolean matchs(byte[] plaintext, byte[] ciphertext) {
        return Arrays.equals(encrypt(plaintext), ciphertext);
    }
}
