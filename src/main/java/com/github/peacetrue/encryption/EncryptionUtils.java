package com.github.peacetrue.encryption;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 密码工具类
 *
 * @author xiayx
 */
public abstract class EncryptionUtils {

    /**
     * 构建rsa公钥
     *
     * @param bytes 二进制公钥
     * @return rsa公钥
     */
    public static PublicKey buildRsaPublicKey(byte[] bytes) {
        try {
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bytes));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 构建rsa私钥
     *
     * @param bytes 二进制私钥
     * @return rsa私钥
     */
    public static PrivateKey buildRsaPrivateKey(byte[] bytes) {
        try {
            return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(bytes));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
