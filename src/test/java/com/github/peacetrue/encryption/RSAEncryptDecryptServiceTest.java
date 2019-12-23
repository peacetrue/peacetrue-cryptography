package com.github.peacetrue.encryption;

import com.github.peacetrue.encoderdecoder.Base64EncoderDecoder;
import com.github.peacetrue.encoderdecoder.StringEncoderDecoder;
import org.junit.Assert;
import org.junit.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * @author xiayx
 */
public class RSAEncryptDecryptServiceTest {

    @Test
    public void generateKey() throws Exception {
        KeyPair keyPair = genKeyPair(1024);
        System.out.println("PublicKey:" + toString(keyPair.getPublic()));
        System.out.println("PrivateKey:" + toString(keyPair.getPrivate()));
    }

    //生成密钥对
    public static KeyPair genKeyPair(int keyLength) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keyLength);
        return keyPairGenerator.generateKeyPair();
    }

    public static String toString(PublicKey publicKey) throws Exception {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public static String toString(PrivateKey privateKey) throws Exception {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    public static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCeIDqZcfdjIhC0IOh4JwEuZAq1y2kd79M/xOKOC7t4a8KfsJrzT3E0ulrwkLDzDjpeQTY1FA5C+oTygSAU9E+cWghP3B0a21jrzxxgP/9X45bTGhbc9S6o3vVntaTNRkYJyqV52YX9uOwNwxVaZEyu0fDAV/yMhzteA7Pqihz/TQIDAQAB";
    public static final String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAJ4gOplx92MiELQg6HgnAS5kCrXLaR3v0z/E4o4Lu3hrwp+wmvNPcTS6WvCQsPMOOl5BNjUUDkL6hPKBIBT0T5xaCE/cHRrbWOvPHGA//1fjltMaFtz1Lqje9We1pM1GRgnKpXnZhf247A3DFVpkTK7R8MBX/IyHO14Ds+qKHP9NAgMBAAECgYEAivax6QZCLgnS+iptgqJspFNhIjEYOSn50fH6VHE1GPhC+0oecrOfPoKDxySxjXk0jH7s36q3ed9mFSSriB7JErGB28e9z1c8TYPWt9lyWh6Uf6zOAy6J2UtPKWwM3DSxY10UOIihaX7PI/ZSARgyXy1McbzwMPErkGTu/TXkczUCQQDXumNp9sAKj50CBPH0gF//I93PjcFkyH9bV7EInJDUxr8FT5fu9XF7Qa4ViWNnYrO6FatkRcElEY0ocVgm4MujAkEAu6UKf8nTi7zNGbFUfMwQ2tdNs7FYxpurdmUmXVpXAfS99N/x8MdcZCw2czemIrXz0qowRKsFSN647YEebCu4TwJAVct63hmUJp8XAkaVM3gatY2+GV13HG3guKMXdSVPNFAWiDYvZomWqD5mzYnmTqeics9LaoWTWvqv+PWa6VtdoQJBAIg6QowRn3ZUhGaBuj+7+wSVyMCLIVNXEr0qpBakDoTKBNpxdvmCEs5a1tEA9qk+hZxMmCxSKM0FfSZYVhjcCLsCQQDTvq8rxpsIlPmyUPFOS6DGbt2G4t9Ue+y/mDWnL5UjUC8C92ohKvhZQX4J67pKw/Ieu/7rd5v6YX5/rDA3BfSs";

    @Test
    public void fixed() throws Exception {
        RSAEncryptDecryptService service = new RSAEncryptDecryptService();
        service.setPublicKey(EncryptionUtils.buildRsaPublicKey(Base64EncoderDecoder.DEFAULT.decode(publicKey)));
        service.setPrivateKey(EncryptionUtils.buildRsaPrivateKey(Base64EncoderDecoder.DEFAULT.decode(privateKey)));
        byte[] decode = StringEncoderDecoder.UTF8.decode("王晓玉");
        byte[] encrypt = service.encrypt(decode);
        Assert.assertArrayEquals(decode, service.decrypt(encrypt));
    }

    @Test
    public void random() throws Exception {
        RSAEncryptDecryptService service = new RSAEncryptDecryptService();
        KeyPair keyPair = genKeyPair(1024);
        service.setPublicKey(keyPair.getPublic());
        service.setPrivateKey(keyPair.getPrivate());
        byte[] decode = StringEncoderDecoder.UTF8.decode("王晓玉");
        byte[] encrypt = service.encrypt(decode);
        Assert.assertArrayEquals(decode, service.decrypt(encrypt));
    }

    /** 微派 */
    @Test
    public void micropie() {
        RSAEncryptDecryptService service = new RSAEncryptDecryptService();
        service.setPublicKey(EncryptionUtils.buildRsaPublicKey(Base64EncoderDecoder.DEFAULT.decode(publicKey)));
        service.setPrivateKey(EncryptionUtils.buildRsaPrivateKey(Base64EncoderDecoder.DEFAULT.decode(privateKey)));
        String plaintext = "wangxiaoyu";
        byte[] encrypt = service.encrypt(StringEncoderDecoder.UTF8.decode(plaintext));
        System.out.println(Base64EncoderDecoder.DEFAULT.encode(encrypt));
        Assert.assertEquals(plaintext, StringEncoderDecoder.UTF8.encode(service.decrypt(encrypt)));
    }

}