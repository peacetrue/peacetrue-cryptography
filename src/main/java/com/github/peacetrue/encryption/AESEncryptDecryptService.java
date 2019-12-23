package com.github.peacetrue.encryption;

import lombok.Getter;
import lombok.Setter;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * AES可逆加解密服务
 * <p>
 * //TODO 实现代码并不全面，对AES了解有限
 *
 * @author xiayx
 * @see <a href="http://www.ssleye.com/aes_cipher.html">在线工具</a>
 * @see <a href="https://blog.csdn.net/u011781521/article/details/77932321">JAVA AES加密与解密</a>
 */
@Setter
@Getter
public class AESEncryptDecryptService implements EncryptDecryptService {

    public static final AESEncryptDecryptService DEFAULT = new AESEncryptDecryptService();
    public static final String AES = "AES";

    /** 秘钥，128|192|256（16|24|32） */
    private SecretKey secretKey;
    /** 模式：CBC、ECB、CFB、OFB、CTR */
    private String mode = "CBC";
    /** 填充：NoPadding、Pkcs5Padding、Pkcs7Padding、Iso7816Padding、Ansix923Padding */
    private String padding = "Pkcs5Padding";
    /** 偏移量 */
    private byte[] offset;

    @Override
    public byte[] encrypt(byte[] ciphertext) {
        try {
            Cipher cipher = Cipher.getInstance(String.format("%s/%s/%s", AES, mode, padding));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(offset));
            return cipher.doFinal(ciphertext);
        } catch (Exception e) {
            throw new IllegalStateException("加密异常", e);
        }
    }

    @Override
    public byte[] decrypt(byte[] plaintext) {
        try {
            Cipher cipher = Cipher.getInstance(String.format("%s/%s/%s", AES, mode, padding));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(offset));
            return cipher.doFinal(plaintext);
        } catch (Exception e) {
            throw new IllegalStateException("解密异常", e);
        }
    }

}
