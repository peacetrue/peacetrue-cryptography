package com.github.peacetrue.codec;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.nio.charset.StandardCharsets;

/**
 * @author peace
 */
class CharsetCodecTest {

    @Test
    void codec() {
        byte[] bytes = RandomStringUtils.random(10).getBytes(StandardCharsets.UTF_8);
        String string = Codec.CHARSET_UTF8.encode(bytes);
        Assertions.assertArrayEquals(bytes, Codec.CHARSET_UTF8.decode(string));

        Assertions.assertDoesNotThrow((ThrowingSupplier<CharsetCodec>) CharsetCodec::new);

    }

}
