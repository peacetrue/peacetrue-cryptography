package com.github.peacetrue.signature;

import com.github.peacetrue.lang.UncheckedException;

import javax.annotation.Nullable;
import java.security.*;
import java.util.Objects;

/**
 * 非对称加密签名者，符合 JDK 签名算法规范。
 *
 * @author peace
 * @see DigestSigner
 **/
public class AsymmetricSigner implements BytesSigner {

    /** 签名算法 */
    private final String algorithm;
    /** 私钥，用于生成签名 */
    private final PrivateKey privateKey;
    /** 公钥，用于验证签名 */
    private final PublicKey publicKey;

    public AsymmetricSigner(@Nullable PrivateKey privateKey, @Nullable PublicKey publicKey) {
        this("SHA256WithRSA", privateKey, publicKey);
    }

    public AsymmetricSigner(String algorithm, @Nullable PrivateKey privateKey, @Nullable PublicKey publicKey) {
        this.algorithm = Objects.requireNonNull(algorithm);
        checkKeyPair(publicKey, privateKey);
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    /**
     * 公钥和私钥至少有一个，通常是 A 端签名 B 端验证或者 B 端签名 A 端验证，则至少要有一个钥匙
     *
     * @param publicKey  公钥
     * @param privateKey 私钥
     */
    public static void checkKeyPair(@Nullable PublicKey publicKey, @Nullable PrivateKey privateKey) {
        if (publicKey == null && privateKey == null) {
            throw new IllegalArgumentException("publicKey or privateKey require at least one");
        }
    }

    @Override
    public byte[] sign(byte[] toBeSigned) {
        try {
            Signature signature = Signature.getInstance(algorithm);
            signature.initSign(privateKey);
            signature.update(toBeSigned);
            return signature.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new UncheckedException(e);
        }
    }

    @Override
    public boolean verify(byte[] toBeSigned, byte[] signedValue) {
        try {
            Signature signature = Signature.getInstance(algorithm);
            signature.initVerify(publicKey);
            signature.update(toBeSigned);
            return signature.verify(signedValue);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new UncheckedException(e);
        }
    }

}
