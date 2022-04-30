package com.github.peacetrue.crypto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.ByteBuffer;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Objects;

/**
 * AES 加解密器。
 *
 * @author peace
 * @see <a href="https://mkyong.com/java/java-aes-encryption-and-decryption/">示例</a>
 */
public class AESEncryptor implements ByteEncryptor {

    public static final String TRANSFORMATION_ECB = "AES/ECB/PKCS5Padding";
    public static final String TRANSFORMATION_CBC = "AES/CBC/PKCS5Padding";
    private static final int IV_LENGTH = 16;
    private static final IVGenerator IV_GENERATOR = new IVGenerator() {
        @Override
        public byte[] generateIV() {
            return randomIV(IV_LENGTH);
        }

        @Override
        public byte[] mergeCiphertextIV(byte[] ciphertext, byte[] iv) {
            return appendIV(ciphertext, iv);
        }
    };

    private static final IVExtractor IV_EXTRACTOR = ciphertextIV -> new CiphertextIV(
            extractCiphertext(ciphertextIV, IV_LENGTH),
            extractIV(ciphertextIV, IV_LENGTH)
    );


    private final String transformation;
    private final SecretKey secretKey;
    private IvParameterSpec ivParameterSpec;
    private IVGenerator ivGenerator;
    private IVExtractor ivExtractor;

    /**
     * 不需要 IV 时使用，例如：EBC 模式
     *
     * @param transformation 传输信息
     * @param secretKey      密钥
     */
    public AESEncryptor(String transformation, SecretKey secretKey) {
        this.transformation = Objects.requireNonNull(transformation);
        this.secretKey = Objects.requireNonNull(secretKey);
    }

    /**
     * 使用静态 IV，可在 CBC 模式下使用。
     * 加解密都使用该 IV。
     *
     * @param transformation  传输信息
     * @param secretKey       密钥
     * @param ivParameterSpec 初始向量
     */
    public AESEncryptor(String transformation, SecretKey secretKey, IvParameterSpec ivParameterSpec) {
        this(transformation, secretKey);
        this.ivParameterSpec = Objects.requireNonNull(ivParameterSpec);
    }

    /**
     * 使用动态 IV，可在 CBC 模式下使用。
     * 加密时，将 IV 合并到密文中；
     * 解密时，从密文中找出动态 IV 后，使用该动态 IV 解密
     *
     * @param transformation 传输信息
     * @param secretKey      密钥
     * @param ivGenerator    初始向量生成器
     * @param ivExtractor    初始向量提取器
     */
    private AESEncryptor(String transformation, SecretKey secretKey, IVGenerator ivGenerator, IVExtractor ivExtractor) {
        this(transformation, secretKey);
        this.ivGenerator = ivGenerator;
        this.ivExtractor = ivExtractor;
    }

    /**
     * 构建 ECB 模式 AES 加解密器。
     *
     * @param secretKey 密钥
     * @return AES 加解密器。
     */
    public static AESEncryptor buildECB(SecretKey secretKey) {
        return new AESEncryptor(TRANSFORMATION_ECB, secretKey);
    }

    /**
     * 构建 CBC 模式静态 AES 加解密器。
     *
     * @param secretKey 密钥
     * @param iv        固定初始向量
     * @return AES 加解密器。
     */
    @SuppressWarnings("java:S3329")
    public static AESEncryptor buildCBC(SecretKey secretKey, byte[] iv) {
        return new AESEncryptor(TRANSFORMATION_CBC, secretKey, new IvParameterSpec(iv, 0, 16));
    }

    /**
     * 构建 CBC 模式动态 AES 加解密器，每次加密随机生成初始向量。
     *
     * @param secretKey 密钥
     * @return AES 加解密器。
     */
    public static AESEncryptor buildCBC(SecretKey secretKey) {
        return new AESEncryptor(TRANSFORMATION_CBC, secretKey, IV_GENERATOR, IV_EXTRACTOR);
    }

    @Override
    public byte[] encrypt(byte[] plaintext) {
        return ivParameterSpec == null
                ? encrypt(transformation, secretKey, ivGenerator, plaintext)
                : ByteEncryptor.encrypt(transformation, secretKey, ivParameterSpec, plaintext);
    }

    @Override
    public byte[] decrypt(byte[] ciphertext) {
        return ivParameterSpec == null
                ? decrypt(transformation, secretKey, ivExtractor, ciphertext)
                : ByteEncryptor.decrypt(transformation, secretKey, ivParameterSpec, ciphertext);
    }

    private static byte[] randomIV(int ivLength) {
        byte[] iv = new byte[ivLength];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    private static byte[] appendIV(byte[] ciphertext, byte[] iv) {
        return ByteBuffer.allocate(ciphertext.length + iv.length).put(ciphertext).put(iv).array();
    }

    private static byte[] extractIV(byte[] ciphertextIV, int ivLength) {
        byte[] iv = new byte[ivLength];
        System.arraycopy(ciphertextIV, ciphertextIV.length - iv.length, iv, 0, iv.length);
        return iv;
    }

    private static byte[] extractCiphertext(byte[] ciphertextIV, int ivLength) {
        byte[] ciphertext = new byte[ciphertextIV.length - ivLength];
        System.arraycopy(ciphertextIV, 0, ciphertext, 0, ciphertext.length);
        return ciphertext;
    }

    @SuppressWarnings("java:S3329")
    private static byte[] encrypt(String transformation, Key key, @Nullable IVGenerator generator, byte[] plaintext) {
        if (generator == null) return ByteEncryptor.encrypt(transformation, key, null, plaintext);
        byte[] iv = generator.generateIV();
        byte[] ciphertext = ByteEncryptor.encrypt(transformation, key, new IvParameterSpec(iv), plaintext);
        return generator.mergeCiphertextIV(ciphertext, iv);
    }

    @SuppressWarnings("java:S3329")
    private static byte[] decrypt(String transformation, Key key, @Nullable IVExtractor extractor, byte[] ciphertext) {
        if (extractor == null) return ByteEncryptor.decrypt(transformation, key, null, ciphertext);
        CiphertextIV ciphertextIV = extractor.extractCiphertextIV(ciphertext);
        return ByteEncryptor.decrypt(transformation, key, new IvParameterSpec(ciphertextIV.getIv()), ciphertextIV.getCiphertext());
    }

    private interface IVGenerator {
        byte[] generateIV();

        byte[] mergeCiphertextIV(byte[] ciphertext, byte[] iv);
    }

    private interface IVExtractor {
        CiphertextIV extractCiphertextIV(byte[] ciphertextIV);
    }

    @Data
    @AllArgsConstructor
    private static class CiphertextIV {
        private byte[] ciphertext;
        private byte[] iv;
    }
}
