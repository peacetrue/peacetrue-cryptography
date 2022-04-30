package com.github.peacetrue.digest;

import com.github.peacetrue.lang.UncheckedException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Hash 摘要者。
 *
 * @author peace
 **/
public interface HashDigester extends Digester<byte[]> {

    HashDigester MD5 = message -> digest("MD5", message);
    HashDigester SHA256 = message -> digest("SHA-256", message);
    HashDigester SHA512 = message -> digest("SHA-512", message);

    @Override
    default boolean match(byte[] message, byte[] digest) {
        return Arrays.equals(digest(message), digest);
    }

    /**
     * 生成消息摘要。
     *
     * @param algorithm 算法
     * @param message   消息
     * @return 摘要
     */
    static byte[] digest(String algorithm, byte[] message) {
        try {
            return MessageDigest.getInstance(algorithm).digest(message);
        } catch (NoSuchAlgorithmException e) {
            throw new UncheckedException(e);
        }
    }

}
