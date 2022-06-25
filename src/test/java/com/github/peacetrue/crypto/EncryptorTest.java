package com.github.peacetrue.crypto;

import com.github.peacetrue.CryptologyUtils;
import com.github.peacetrue.codec.Codec;
import com.github.peacetrue.lang.UncheckedException;
import com.github.peacetrue.security.KeyPairGeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.security.KeyPair;

import static com.github.peacetrue.CryptologyUtils.getAESKey;
import static com.github.peacetrue.codec.Codec.CHARSET_UTF8;
import static com.github.peacetrue.security.KeyPairGeneratorUtils.KEY_LENGTH_1024;

/**
 * @author peace
 * @see <a href="https://the-x.cn/cryptography/Aes.aspx">在线工具</a>
 **/
@Slf4j
class EncryptorTest {

    @Test
    void encryptAES_ECB() throws Exception {
        AESEncryptor errorAESEncryptor = new AESEncryptor("AES2/ECB/PKCS5Padding", getAESKey(128));
        Assertions.assertThrows(UncheckedException.class, () -> errorAESEncryptor.encrypt(CHARSET_UTF8.decode("1")));
        Assertions.assertThrows(UncheckedException.class, () -> errorAESEncryptor.decrypt(CHARSET_UTF8.decode("1")));

        SecretKey secretKey = getAESKey(128);
        CodecEncryptor encryptor = new CodecEncryptor(AESEncryptor.buildECB(secretKey));
        String plaintext = "111";
        String ciphertext = encryptor.encrypt(plaintext);
        Assertions.assertEquals(plaintext, encryptor.decrypt(ciphertext));
    }

    @Test
    void encryptAES_CBC_Static() throws Exception {
        SecretKey secretKey = getAESKey(128);
        log.info("secretKey(HEX): {}", Codec.HEX.encode(secretKey.getEncoded()));
        byte[] iv = CryptologyUtils.randomBytes(16);
        log.info("IV(HEX): {}", Codec.HEX.encode(iv));
        CodecEncryptor encryptor = new CodecEncryptor(AESEncryptor.buildCBC(secretKey, iv), CHARSET_UTF8, Codec.BASE64);
        String plaintext = "111";
        log.info("plaintext(UTF-8): {}", plaintext);
        String ciphertext = encryptor.encrypt(plaintext);
        log.info("ciphertext(Base64): {}", ciphertext);
        Assertions.assertEquals(plaintext, encryptor.decrypt(ciphertext));
    }

    @Test
    void encryptAES_CBC_Dynamic() throws Exception {
        SecretKey secretKey = getAESKey(128);
        CodecEncryptor encryptor = new CodecEncryptor(AESEncryptor.buildCBC(secretKey));
        String plaintext = "111";
        String ciphertext = encryptor.encrypt(plaintext);
        Assertions.assertEquals(plaintext, encryptor.decrypt(ciphertext));
    }

    @Test
    void encryptRSA_ECB() {
        KeyPair keyPair = KeyPairGeneratorUtils.generateRsaKeyPair(KEY_LENGTH_1024);
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RSAEncryptor("", null, null));
        Assertions.assertDoesNotThrow(() -> new RSAEncryptor("", keyPair.getPublic(), null));
        Assertions.assertDoesNotThrow(() -> new RSAEncryptor("", null, keyPair.getPrivate()));

        RSAEncryptor rsaEncryptor = RSAEncryptor.buildECB(keyPair.getPublic(), keyPair.getPrivate());
        CodecEncryptor encryptor = new CodecEncryptor(rsaEncryptor);
        String plaintext = "111";
        String ciphertext = encryptor.encrypt(plaintext);
        Assertions.assertEquals(plaintext, encryptor.decrypt(ciphertext));
    }


}
