package com.github.peacetrue.encryption;

import com.github.peacetrue.encoderdecoder.HexEncoderDecoder;
import org.junit.Assert;
import org.junit.Test;

import static com.github.peacetrue.encoderdecoder.StringEncoderDecoder.UTF8;

/**
 * @author xiayx
 */
public class MD5EncryptServiceTest {

    @Test
    public void encrypt() {
        String encrypt = HexEncoderDecoder.DEFAULT.encode(MD5EncryptService.DEFAULT.encrypt(UTF8.decode("王晓玉")));
        Assert.assertEquals("20323450AB84F7577C59337DBE5BF8DA", encrypt);
    }
}