package com.github.peacetrue.signature;

import com.github.peacetrue.codec.Codec;

import java.util.Objects;

/**
 * 编解码代理签名者。
 *
 * @author peace
 **/
public class CodecSigner implements StringSigner {

    private final Signer<byte[], byte[]> signer;
    private final Codec outer;
    private final Codec inner;

    /**
     * 接收 UTF8 字符串，并输出 HEX 签名。
     *
     * @param signer 字节数组签名者
     */
    public CodecSigner(Signer<byte[], byte[]> signer) {
        this(signer, Codec.CHARSET_UTF8, Codec.HEX);
    }

    /**
     * 自定义一个字符串签名者。
     *
     * @param signer 字节数组签名者
     * @param outer  外层编解码器
     * @param inner  内层编解码器
     */
    public CodecSigner(Signer<byte[], byte[]> signer, Codec outer, Codec inner) {
        this.signer = Objects.requireNonNull(signer);
        this.outer = Objects.requireNonNull(outer);
        this.inner = Objects.requireNonNull(inner);
    }

    @Override
    public String sign(String toBeSigned) {
        return inner.encode(signer.sign(outer.decode(toBeSigned)));
    }

    @Override
    public boolean verify(String toBeSigned, String signed) {
        return signer.verify(outer.decode(toBeSigned), inner.decode(signed));
    }
}
