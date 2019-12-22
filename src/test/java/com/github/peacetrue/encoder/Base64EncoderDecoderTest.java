package com.github.peacetrue.encoder;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author xiayx
 */
public class Base64EncoderDecoderTest {

    @Test
    public void encode() {
        String encode = Base64EncoderDecoder.DEFAULT.encode(StringEncoderDecoder.UTF8.decode("王晓玉"));
        Assert.assertEquals("546L5pmT546J", encode);
    }

    @Test
    public void decode() {
        byte[] bytes = Base64EncoderDecoder.DEFAULT.decode("546L5pmT546J");
        Assert.assertEquals(StringEncoderDecoder.UTF8.encode(bytes), "王晓玉");
    }

    @Test
    public void both() {
        String ciphertext = "王晓玉";
        String encode = Base64EncoderDecoder.DEFAULT.encode(StringEncoderDecoder.UTF8.decode(ciphertext));
        Assert.assertEquals(ciphertext, StringEncoderDecoder.UTF8.encode(Base64EncoderDecoder.DEFAULT.decode(encode)));
    }
}