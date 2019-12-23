package com.github.peacetrue.encoderdecoder;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author xiayx
 */
public class StringEncoderDecoderTest {

    @Test
    public void both() {
        byte[] encode = StringEncoderDecoder.UTF8.decode("王晓玉");
        Assert.assertEquals("王晓玉", StringEncoderDecoder.UTF8.encode(encode));
    }

}