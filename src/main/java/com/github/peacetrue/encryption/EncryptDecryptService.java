package com.github.peacetrue.encryption;

/**
 * 加解密服务
 *
 * @author xiayx
 */
public interface EncryptDecryptService extends EncryptService {

    /**
     * 解密
     *
     * @param ciphertext 加密后的密文字节数组
     * @return 加密前的明文
     */
    byte[] decrypt(byte[] ciphertext);
}
