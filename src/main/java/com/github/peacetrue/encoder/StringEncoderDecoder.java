package com.github.peacetrue.encoder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 字符串编码解码器
 *
 * @author xiayx
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StringEncoderDecoder implements EncoderDecoder {

    public static final StringEncoderDecoder UTF8 = new StringEncoderDecoder(StandardCharsets.UTF_8);

    /** 字符集 */
    private Charset charset = Charset.defaultCharset();

    @Override
    public String encode(byte[] plaintext) {
        return new String(plaintext, charset);
    }

    @Override
    public byte[] decode(String ciphertext) {
        return ciphertext.getBytes(charset);
    }

}
