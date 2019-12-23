package com.github.peacetrue.encoderdecoder;

import javax.xml.bind.DatatypeConverter;

/**
 * 十六进制编码解码器
 * <p>
 * 编码后结果不区分大小写，默认输出大写
 *
 * @author xiayx
 */
public class HexEncoderDecoder implements EncoderDecoder {

    public static final HexEncoderDecoder DEFAULT = new HexEncoderDecoder();

    private HexEncoderDecoder() {
    }

    @Override
    public String encode(byte[] plaintext) {
        return DatatypeConverter.printHexBinary(plaintext);
    }

    @Override
    public byte[] decode(String ciphertext) {
        return DatatypeConverter.parseHexBinary(ciphertext);
    }
}
