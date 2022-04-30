package com.github.peacetrue.codec;

import javax.xml.bind.DatatypeConverter;

/**
 * 十六进制编解码器。
 * <p>
 * 编码后结果不区分大小写，默认输出大写。
 *
 * @author peace
 */
public class HexCodec implements Codec {

    public static final HexCodec DEFAULT = new HexCodec();

    private HexCodec() {
    }

    @Override
    public String encode(byte[] bytes) {
        return DatatypeConverter.printHexBinary(bytes);
    }

    @Override
    public byte[] decode(String string) {
        return DatatypeConverter.parseHexBinary(string);
    }
}
