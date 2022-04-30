package com.github.peacetrue.crypto;

import javax.annotation.Nullable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Objects;

/**
 * RSA 加解密服务。
 *
 * @author peace
 * @see <a href="https://www.bejson.com/enc/rsa/">在线工具</a>
 */
public class RSAEncryptor implements ByteEncryptor {

    private static final String TRANSFORMATION_DEFAULT = "RSA/ECB/PKCS1Padding";

    private final String transformation;
    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    public RSAEncryptor(String transformation, PublicKey publicKey) {
        this(transformation, Objects.requireNonNull(publicKey), null);
    }

    public RSAEncryptor(String transformation, PrivateKey privateKey) {
        this(transformation, null, Objects.requireNonNull(privateKey));
    }

    public RSAEncryptor(String transformation, @Nullable PublicKey publicKey, @Nullable PrivateKey privateKey) {
        this.transformation = Objects.requireNonNull(transformation);
        if (publicKey == null && privateKey == null) {
            throw new IllegalArgumentException("publicKey or privateKey require at least one");
        }
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public static RSAEncryptor buildECB(@Nullable PublicKey publicKey, @Nullable PrivateKey privateKey) {
        return new RSAEncryptor(TRANSFORMATION_DEFAULT, publicKey, privateKey);
    }

    @Override
    public byte[] encrypt(byte[] plaintext) {
        return ByteEncryptor.encrypt(transformation, Objects.requireNonNull(publicKey), null, plaintext);
    }

    @Override
    public byte[] decrypt(byte[] ciphertext) {
        return ByteEncryptor.decrypt(transformation, Objects.requireNonNull(privateKey), null, ciphertext);
    }

}
