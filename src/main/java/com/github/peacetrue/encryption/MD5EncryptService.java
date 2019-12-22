package com.github.peacetrue.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密服务
 *
 * @author xiayx
 * @see <a href="https://cmd5.la/">在线工具</a>
 */
public class MD5EncryptService implements EncryptService {

    public static final MD5EncryptService DEFAULT = new MD5EncryptService();

    private MD5EncryptService() {
    }

    @Override
    public byte[] encrypt(byte[] plaintext) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plaintext);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5加密异常", e);
        }
    }

}
