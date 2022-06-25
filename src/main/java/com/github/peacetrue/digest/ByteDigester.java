package com.github.peacetrue.digest;

import com.github.peacetrue.lang.UncheckedException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 字节数组摘要者。
 *
 * @author peace
 **/
public interface ByteDigester extends Digester<byte[]> {

    ByteDigester MD5 = message -> digest("MD5", message);
    ByteDigester SHA256 = message -> digest("SHA-256", message);
    ByteDigester SHA512 = message -> digest("SHA-512", message);

    @Override
    default boolean verify(byte[] message, byte[] digest) {
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
