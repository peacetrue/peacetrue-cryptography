package com.github.peacetrue.digest;

import com.github.peacetrue.codec.Codec;

import java.util.Objects;

/**
 * 字符串摘要者。
 *
 * @author peace
 **/
public class StringDigester implements Digester<String> {

    public static final StringDigester MD5 = new StringDigester(HashDigester.MD5);
    public static final StringDigester SHA256 = new StringDigester(HashDigester.SHA256);
    public static final StringDigester SHA512 = new StringDigester(HashDigester.SHA512);

    private final Digester<byte[]> digester;
    private final Codec outer;
    private final Codec inner;

    /**
     * 接收 UTF8 字符串消息，并输出 HEX 摘要
     *
     * @param digester 字节数组摘要者
     */
    public StringDigester(Digester<byte[]> digester) {
        this(digester, Codec.CHARSET_UTF8, Codec.HEX);
    }

    /**
     * 自定义一个字符串摘要者
     *
     * @param digester 字节数组摘要者
     * @param outer    消息解码器
     * @param inner    摘要编码器
     */
    public StringDigester(Digester<byte[]> digester, Codec outer, Codec inner) {
        this.digester = Objects.requireNonNull(digester);
        this.outer = Objects.requireNonNull(outer);
        this.inner = Objects.requireNonNull(inner);
    }

    @Override
    public String digest(String message) {
        return inner.encode(digester.digest(outer.decode(message)));
    }

    @Override
    public boolean match(String message, String digest) {
        return digest(message).equalsIgnoreCase(digest);
    }
}
