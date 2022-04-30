package com.github.peacetrue.crypto;

import com.github.peacetrue.CryptologyUtils;
import com.github.peacetrue.codec.Codec;
import com.github.peacetrue.security.KeyPairGeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.security.KeyPair;

import static com.github.peacetrue.security.KeyPairGeneratorUtils.KEY_LENGTH_1024;

/**
 * @author peace
 * @see <a href="https://the-x.cn/cryptography/Aes.aspx">在线工具</a>
 **/
@Slf4j
class EncryptorTest {

    @Test
    void encryptAES_ECB() throws Exception {
        SecretKey secretKey = CryptologyUtils.getAESKey(128);
        StringEncryptor encryptor = new StringEncryptor(AESEncryptor.buildECB(secretKey));
        String plaintext = "111";
        String ciphertext = encryptor.encrypt(plaintext);
        Assertions.assertEquals(plaintext, encryptor.decrypt(ciphertext));
    }

    @Test
    void encryptAES_CBC_Static() throws Exception {
        SecretKey secretKey = CryptologyUtils.getAESKey(128);
        log.info("secretKey(HEX): {}", Codec.HEX.encode(secretKey.getEncoded()));
        byte[] iv = CryptologyUtils.randomBytes(16);
        log.info("IV(HEX): {}", Codec.HEX.encode(iv));
        StringEncryptor encryptor = new StringEncryptor(AESEncryptor.buildCBC(secretKey, iv), Codec.CHARSET_UTF8, Codec.BASE64);
        String plaintext = "111";
        log.info("plaintext(UTF-8): {}", plaintext);
        String ciphertext = encryptor.encrypt(plaintext);
        log.info("ciphertext(Base64): {}", ciphertext);
        Assertions.assertEquals(plaintext, encryptor.decrypt(ciphertext));
    }

    @Test
    void encryptAES_CBC_Dynamic() throws Exception {
        SecretKey secretKey = CryptologyUtils.getAESKey(128);
        StringEncryptor encryptor = new StringEncryptor(AESEncryptor.buildCBC(secretKey));
        String plaintext = "111";
        String ciphertext = encryptor.encrypt(plaintext);
        Assertions.assertEquals(plaintext, encryptor.decrypt(ciphertext));
    }

    @Test
    void encryptRSA_ECB() {
        KeyPair keyPair = KeyPairGeneratorUtils.generateRsaKeyPair(KEY_LENGTH_1024);
        RSAEncryptor rsaEncryptor = RSAEncryptor.buildECB(keyPair.getPublic(), keyPair.getPrivate());
        StringEncryptor encryptor = new StringEncryptor(rsaEncryptor);
        String plaintext = "111";
        String ciphertext = encryptor.encrypt(plaintext);
        Assertions.assertEquals(plaintext, encryptor.decrypt(ciphertext));
    }


}
