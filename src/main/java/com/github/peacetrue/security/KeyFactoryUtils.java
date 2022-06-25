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
 * @see KeyFactory
 **/
public abstract class KeyFactoryUtils {

    /** 抽象工具类，防止实例化 */
    private KeyFactoryUtils() {
    }

    /**
     * 获取 RSA 钥匙工厂。
     *
     * @return RSA 实例
     */
    public static KeyFactory getRsaInstance() {
        return getKeyFactory("RSA");
    }

    static KeyFactory getKeyFactory(String algorithm) {
        try {
            return KeyFactory.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new UncheckedException(e);
        }
    }

    /**
     * 构建 RSA 私钥。
     *
     * @param encodedKey 二进制私钥
     * @return RSA 私钥
     */
    public static PrivateKey generateRsaPrivate(byte[] encodedKey) {
        try {
            return getRsaInstance().generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
        } catch (InvalidKeySpecException e) {
            throw new UncheckedException(e);
        }
    }

    /**
     * 构建 RSA 公钥。
     *
     * @param encodeKey 二进制公钥
     * @return RSA 公钥
     */
    public static PublicKey generateRsaPublic(byte[] encodeKey) {
        try {
            return getRsaInstance().generatePublic(new X509EncodedKeySpec(encodeKey));
        } catch (InvalidKeySpecException e) {
            throw new UncheckedException(e);
        }
    }
}
