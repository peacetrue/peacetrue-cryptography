package com.github.peacetrue.codec;

/**
 * 编解码器 (Codec=coder/decoder) 。
 *
 * @author peace
 */
public interface Codec {

    Codec CHARSET_UTF8 = CharsetCodec.UTF8;
    Codec HEX = HexCodec.DEFAULT;
    Codec BASE64 = Base64Codec.DEFAULT;

    /**
     * 编码，将二进制数组编码成人类可读的字符串。
     *
     * @param bytes 二进制数组
     * @return 编码后的字符串
     */
    String encode(byte[] bytes);

    /**
     * 解码，将人类可读的字符串解码成二进制数组。
     *
     * @param string 编码后的字符串
     * @return 原始二进制数组
     */
    byte[] decode(String string);
}
