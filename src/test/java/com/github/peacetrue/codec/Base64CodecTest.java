package com.github.peacetrue.codec;

import com.github.peacetrue.CryptologyUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author peace
 */
@Slf4j
class Base64CodecTest {

    @Test
    void codec() {
        byte[] bytes = CryptologyUtils.randomBytes();
        String string = Codec.BASE64.encode(bytes);
        log.info("string: {}", string);
        Assertions.assertArrayEquals(bytes, Codec.BASE64.decode(string));
    }

}
