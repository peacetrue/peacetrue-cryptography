package com.github.peacetrue.digest;

import com.github.peacetrue.lang.UncheckedException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * HMAC 摘要者。
 *
 * @author peace
 **/
public class HmacDigester implements HashDigester {

    private final String algorithm;
    private final SecretKey secretKey;

    public HmacDigester(SecretKey secretKey) {
        this(secretKey.getAlgorithm(), secretKey);
    }

    public HmacDigester(String algorithm, SecretKey secretKey) {
        this.algorithm = Objects.requireNonNull(algorithm);
        this.secretKey = Objects.requireNonNull(secretKey);
    }

    /**
     * 构造 HmacSHA256 摘要者。
     *
     * @param secretKey 密钥，长度没有要求
     * @return HMAC 摘要者
     */
    public static HmacDigester buildHmacSHA256(byte[] secretKey) {
        return new HmacDigester(new SecretKeySpec(secretKey, "HmacSHA256"));
    }

    @Override
    public byte[] digest(byte[] message) {
        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secretKey);
            return mac.doFinal(message);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new UncheckedException(e);
        }
    }

}
