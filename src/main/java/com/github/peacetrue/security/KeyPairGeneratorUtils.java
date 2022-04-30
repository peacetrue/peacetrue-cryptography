package com.github.peacetrue.security;

import com.github.peacetrue.codec.Codec;
import com.github.peacetrue.lang.UncheckedException;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * {@link KeyPairGenerator} 工具类
 *
 * @author peace
 **/
@Slf4j
public abstract class KeyPairGeneratorUtils {

    public static final int KEY_LENGTH_1024 = 1024;
    public static final int KEY_LENGTH_2048 = 2048;

    protected KeyPairGeneratorUtils() {
    }

    public static KeyPairGenerator getRsaKeyPairGenerator() {
        try {
            return KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new UncheckedException(e);
        }
    }

    public static KeyPair generateRsaKeyPair(int keyLength) {
        KeyPairGenerator keyPairGenerator = getRsaKeyPairGenerator();
        keyPairGenerator.initialize(keyLength);
        return keyPairGenerator.generateKeyPair();
    }

    public static void printRsaKeyPair(int keyLength, Codec codec) {
        KeyPair keyPair = generateRsaKeyPair(keyLength);
        log.info("PublicKey: {}", toString(keyPair.getPublic(), codec));
        log.info("PrivateKey: {}", toString(keyPair.getPrivate(), codec));
    }

    public static String toString(Key key, Codec codec) {
        return codec.encode(key.getEncoded());
    }

}
