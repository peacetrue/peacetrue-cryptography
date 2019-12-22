package com.github.peacetrue.encryption;

import com.github.peacetrue.encoder.Base64EncoderDecoder;
import com.github.peacetrue.encoder.EncoderDecoder;
import com.github.peacetrue.encoder.HexEncoderDecoder;
import com.github.peacetrue.encoder.StringEncoderDecoder;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * @author xiayx
 */
public class AESEncryptDecryptServiceTest {

    @Test
    public void keyGenerate() throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128, new SecureRandom("王晓玉".getBytes(StandardCharsets.UTF_8)));
        SecretKey secretKey = generator.generateKey();
        System.out.println(Arrays.toString(secretKey.getEncoded()));
        System.out.println(new String(secretKey.getEncoded()));
    }

    public String encrypt(String plaintext, EncoderDecoder encoderDecoder) {
        AESEncryptDecryptService service = new AESEncryptDecryptService();
        service.setSecretKey(new SecretKeySpec("5e8y6w45ju8w9jq8".getBytes(), AESEncryptDecryptService.AES));
        service.setOffset("5e8y6w45ju8w9jq8".getBytes());
        return encoderDecoder.encode(service.encrypt(StringEncoderDecoder.UTF8.decode(plaintext)));
    }

    public String decrypt(String ciphertext, EncoderDecoder encoderDecoder) {
        AESEncryptDecryptService service = new AESEncryptDecryptService();
        service.setSecretKey(new SecretKeySpec("5e8y6w45ju8w9jq8".getBytes(), AESEncryptDecryptService.AES));
        service.setOffset("5e8y6w45ju8w9jq8".getBytes());
        return StringEncoderDecoder.UTF8.encode(service.decrypt(encoderDecoder.decode(ciphertext)));
    }

    @Test
    public void encryptBase64() {
        Assert.assertEquals("RpEIgsZoNGaGigJSVQ6toQ==", encrypt("王晓玉", Base64EncoderDecoder.DEFAULT));
        System.out.println(encrypt("{ddd}", Base64EncoderDecoder.DEFAULT));
        //RpEIgsZoNGaGigJSVQ6toQ==
    }

    @Test
    public void decryptBase64() {
        Assert.assertEquals("王晓玉", decrypt("RpEIgsZoNGaGigJSVQ6toQ==", Base64EncoderDecoder.DEFAULT));
    }

    @Test
    public void encryptHex() {
        Assert.assertEquals("46910882C6683466868A0252550EADA1", encrypt("王晓玉", HexEncoderDecoder.DEFAULT));
    }

    @Test
    public void decryptHex() {
        Assert.assertEquals("王晓玉", decrypt("46910882C6683466868A0252550EADA1", HexEncoderDecoder.DEFAULT));
    }


}