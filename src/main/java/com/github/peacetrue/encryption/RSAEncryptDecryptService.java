package com.github.peacetrue.encryption;

import lombok.Getter;
import lombok.Setter;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * RSA 加解密服务
 *
 * @author xiayx
 * @see <a href="https://www.bejson.com/enc/rsa/">在线工具</a>
 */
@Getter
@Setter
public class RSAEncryptDecryptService implements EncryptDecryptService {

    public static final String RSA = "RSA";

    /** 公钥 */
    private PublicKey publicKey;
    /** 私钥 */
    private PrivateKey privateKey;
    /** 模式：CBC、ECB、CFB、OFB、CTR */
    private String mode = "ECB";
    /** 填充：NoPadding、PKCS1Padding、Pkcs5Padding、Pkcs7Padding、Iso7816Padding、Ansix923Padding */
    private String padding = "PKCS1Padding";

    @Override
    public byte[] encrypt(byte[] plaintext) {
        try {
            Cipher cipher = Cipher.getInstance(String.format("%s/%s/%s", RSA, mode, padding));
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(plaintext);
        } catch (Exception e) {
            throw new IllegalArgumentException("RAS加密异常", e);
        }
    }

    @Override
    public byte[] decrypt(byte[] ciphertext) {
        try {
            Cipher cipher = Cipher.getInstance(String.format("%s/%s/%s", RSA, mode, padding));
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(ciphertext);
        } catch (Exception e) {
            throw new IllegalArgumentException("RAS解密异常", e);
        }
    }

}
