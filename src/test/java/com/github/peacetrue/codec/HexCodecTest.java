package com.github.peacetrue.codec;


import com.github.peacetrue.CryptologyUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author peace
 */
@Slf4j
class HexCodecTest {

    @Test
    void codec() {
        byte[] bytes = CryptologyUtils.randomBytes();
        String string = Codec.HEX.encode(bytes);
        log.info("string: {}",string);
        Assertions.assertArrayEquals(bytes, Codec.HEX.decode(string));
    }

}
