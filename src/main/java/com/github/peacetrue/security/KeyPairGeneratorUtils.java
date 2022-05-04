package com.github.peacetrue.security;

import com.github.peacetrue.lang.UncheckedException;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * {@link KeyPairGenerator} 工具类。
 *
 * @author peace
 **/
public abstract class KeyPairGeneratorUtils {

    public static final int KEY_LENGTH_1024 = 1024;
    public static final int KEY_LENGTH_2048 = 2048;

    /** 抽象工具类，防止实例化 */
    private KeyPairGeneratorUtils() {
    }

    /**
     * 获取 RSA 公私钥对生成器。
     *
     * @return RSA 公私钥对生成器
     */
    public static KeyPairGenerator getRsaKeyPairGenerator() {
        try {
            return KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new UncheckedException(e);
        }
    }

    /**
     * 生成 RSA 公私钥对。
     *
     * @param keyLength 钥匙长度
     * @return RSA 公私钥对
     */
    public static KeyPair generateRsaKeyPair(int keyLength) {
        KeyPairGenerator keyPairGenerator = getRsaKeyPairGenerator();
        keyPairGenerator.initialize(keyLength);
        return keyPairGenerator.generateKeyPair();
    }

}
