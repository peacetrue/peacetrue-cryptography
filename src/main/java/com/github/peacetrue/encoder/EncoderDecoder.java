package com.github.peacetrue.encoder;

/**
 * 编码器和解码器
 * <p>
 * 在二进制数组和可读性字符串之间的转换
 *
 * @author xiayx
 */
public interface EncoderDecoder {

    /**
     * 编码，将二进制数组编码成人类可读的字符串
     *
     * @param plaintext 二进制数组
     * @return 编码后的字符串
     */
    String encode(byte[] plaintext);

    /**
     * 解码，将人类可读的字符串解码成二进制数组
     *
     * @param ciphertext 编码后的字符串
     * @return 原始二进制数组
     */
    byte[] decode(String ciphertext);
}
