package com.github.peacetrue.encoder;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author xiayx
 */
public class HexEncoderDecoderTest {

    @Test
    public void encode() {
        String encode = HexEncoderDecoder.DEFAULT.encode(StringEncoderDecoder.UTF8.decode("王晓玉"));
        Assert.assertEquals("E78E8BE69993E78E89", encode);
    }

    @Test
    public void decode() {
        byte[] bytes = HexEncoderDecoder.DEFAULT.decode("E78E8BE69993E78E89");
        Assert.assertEquals(StringEncoderDecoder.UTF8.encode(bytes), "王晓玉");
    }

    @Test
    public void both() {
        String ciphertext = "王晓玉";
        String encode = HexEncoderDecoder.DEFAULT.encode(StringEncoderDecoder.UTF8.decode(ciphertext));
        Assert.assertEquals(ciphertext, StringEncoderDecoder.UTF8.encode(HexEncoderDecoder.DEFAULT.decode(encode)));
    }
}