package com.github.peacetrue.codec;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 字符集编解码器。
 *
 * @author peace
 */
public class CharsetCodec implements Codec {

    public static final CharsetCodec UTF8 = new CharsetCodec(StandardCharsets.UTF_8);

    /** 字符集 */
    private final Charset charset;

    public CharsetCodec() {
        this(Charset.defaultCharset());
    }

    public CharsetCodec(Charset charset) {
        this.charset = Objects.requireNonNull(charset);
    }

    @Override
    public String encode(byte[] bytes) {
        return new String(bytes, charset);
    }

    @Override
    public byte[] decode(String string) {
        return string.getBytes(charset);
    }

}
