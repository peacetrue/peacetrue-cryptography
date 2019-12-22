package com.github.peacetrue.encryption;

import javax.xml.bind.DatatypeConverter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 密码工具类
 *
 * @author xiayx
 */
public abstract class EncryptionUtils {

    /** hex 编码方式 */
    public static final String HEX = "Hex";
    /** Base64 编码方式 */
    public static final String BASE_64 = "Base64";

    /**
     * 编码
     *
     * @param plaintext 待编码的明文
     * @param type      编码类型，包括 {@link #HEX} 和 {@link #BASE_64}
     * @return 编码后的密文
     */
    public static String encode(byte[] plaintext, String type) {
        switch (type) {
            case HEX:
                return DatatypeConverter.printHexBinary(plaintext);
            case BASE_64:
                return Base64.getEncoder().encodeToString(plaintext);
            default:
                throw new IllegalArgumentException(String.format("无法识别的类型参数[%s]", type));
        }
    }


    /**
     * 解码
     *
     * @param ciphertext 待解码的密文
     * @param type       解码类型，包括 {@link #HEX} 和 {@link #BASE_64}
     * @return 解码后的明文
     */
    public static byte[] decode(String ciphertext, String type) {
        switch (type) {
            case HEX:
                return DatatypeConverter.parseHexBinary(ciphertext);
            case BASE_64:
                return Base64.getDecoder().decode(ciphertext);
            default:
                throw new IllegalArgumentException(String.format("无法识别的类型参数[%s]", type));
        }
    }

    public static PublicKey buildRsaPublicKey(byte[] bytes) {
        try {
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bytes));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static PrivateKey buildRsaPrivateKey(byte[] bytes) {
        try {
            return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(bytes));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
