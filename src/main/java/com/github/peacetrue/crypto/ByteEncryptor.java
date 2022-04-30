package com.github.peacetrue.crypto;

import com.github.peacetrue.lang.UncheckedException;

import javax.annotation.Nullable;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * 字节数组加解密器。
 *
 * @author peace
 */
public interface ByteEncryptor extends Encryptor<byte[]> {

    /**
     * 加密。
     *
     * @param transformation  转换
     * @param key             钥，公钥或私钥或密钥。
     * @param ivParameterSpec 初始向量
     * @param plaintext       明文
     * @return 密文
     */
    static byte[] encrypt(String transformation, Key key, @Nullable IvParameterSpec ivParameterSpec, byte[] plaintext) {
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            return cipher.doFinal(plaintext);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException
                | InvalidAlgorithmParameterException | BadPaddingException e) {
            throw new UncheckedException(e);
        }
    }

    /**
     * 解密。
     *
     * @param transformation  转换
     * @param key             钥，公钥或私钥或密钥。
     * @param ivParameterSpec 初始向量
     * @param ciphertext      密文
     * @return 明文
     */

    static byte[] decrypt(String transformation, Key key, @Nullable IvParameterSpec ivParameterSpec, byte[] ciphertext) {
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
            return cipher.doFinal(ciphertext);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException
                | InvalidAlgorithmParameterException | BadPaddingException e) {
            throw new UncheckedException(e);
        }
    }

}
