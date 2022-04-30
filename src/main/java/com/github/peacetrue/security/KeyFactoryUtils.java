package com.github.peacetrue.security;

import com.github.peacetrue.lang.UncheckedException;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * {@link KeyFactory} 工具类
 *
 * @author peace
 * @see java.security.KeyFactory
 **/
public abstract class KeyFactoryUtils {

    protected KeyFactoryUtils() {
    }

    public static KeyFactory getRsaInstance() {
        try {
            return KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new UncheckedException(e);
        }
    }

    /**
     * 构建 rsa 私钥
     *
     * @param bytes 二进制私钥
     * @return rsa 私钥
     */
    public static PrivateKey generateRsaPrivate(byte[] bytes) {
        try {
            return getRsaInstance().generatePrivate(new PKCS8EncodedKeySpec(bytes));
        } catch (InvalidKeySpecException e) {
            throw new UncheckedException(e);
        }
    }

    /**
     * 构建 rsa 公钥
     *
     * @param bytes 二进制公钥
     * @return rsa 公钥
     */
    public static PublicKey generateRsaPublic(byte[] bytes) {
        try {
            return getRsaInstance().generatePublic(new X509EncodedKeySpec(bytes));
        } catch (InvalidKeySpecException e) {
            throw new UncheckedException(e);
        }
    }
}
