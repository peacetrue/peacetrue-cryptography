package com.github.peacetrue.encryption;

import com.github.peacetrue.encoder.Base64EncoderDecoder;
import com.github.peacetrue.encoder.StringEncoderDecoder;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * 微派测试
 *
 * <pre>
 *     1.随机生成AES秘钥A
 *     2.用AES秘钥A加密请求内容B
 *     3.配置RSA公钥C
 *     4.用RSA加密A
 * </pre>
 *
 * @author xiayx
 */
public class MicropieTest {

    @Test
    public void encrypt() throws Exception {
        String content = "{ddd}";
        SignedData signedData = encrypt(content, RSAEncryptDecryptServiceTest.publicKey);
        System.out.println(signedData.getEncryptkey());
        System.out.println(signedData.getReqData());
        String decrypt = decrypt(signedData, RSAEncryptDecryptServiceTest.privateKey);
        Assert.assertEquals(content, decrypt);
    }

    public SignedData encrypt(String content, String publicKey) throws Exception {
        SignedData signedData = new SignedData();

        AESEncryptDecryptService service = new AESEncryptDecryptService();
//        service.setSecretKey(KeyGenerator.getInstance("AES").generateKey());
        service.setSecretKey(new SecretKeySpec("5e8y6w45ju8w9jq8".getBytes(), AESEncryptDecryptService.AES));
        service.setOffset("5e8y6w45ju8w9jq8".getBytes());
        String encryptedContent = Base64EncoderDecoder.DEFAULT.encode(service.encrypt(StringEncoderDecoder.UTF8.decode(content)));
        signedData.setReqData(encryptedContent);

        RSAEncryptDecryptService rsaService = new RSAEncryptDecryptService();
        rsaService.setPublicKey(EncryptionUtils.buildRsaPublicKey(Base64EncoderDecoder.DEFAULT.decode(publicKey)));
        System.out.println("secretKey:" + StringEncoderDecoder.UTF8.encode(service.getSecretKey().getEncoded()));
        signedData.setEncryptkey(Base64EncoderDecoder.DEFAULT.encode(rsaService.encrypt(service.getSecretKey().getEncoded())));

        signedData.setMerchant_code("hjgh");
        return signedData;
    }

    public String decrypt(SignedData signedData, String privateKey) throws Exception {
        RSAEncryptDecryptService rsaService = new RSAEncryptDecryptService();
        rsaService.setPrivateKey(EncryptionUtils.buildRsaPrivateKey(Base64EncoderDecoder.DEFAULT.decode(privateKey)));
        byte[] decrypt = rsaService.decrypt(Base64EncoderDecoder.DEFAULT.decode(signedData.getEncryptkey()));
        System.out.println("secretKey:" + StringEncoderDecoder.UTF8.encode(decrypt));

        AESEncryptDecryptService service = new AESEncryptDecryptService();
        service.setSecretKey(new SecretKeySpec(decrypt, AESEncryptDecryptService.AES));
        service.setOffset("5e8y6w45ju8w9jq8".getBytes());
        return StringEncoderDecoder.UTF8.encode(service.decrypt(Base64EncoderDecoder.DEFAULT.decode(signedData.getReqData())));
    }

    @Data
    public static class SignedData {
        private String reqData;
        private String encryptkey;
        private String merchant_code;
    }
}
