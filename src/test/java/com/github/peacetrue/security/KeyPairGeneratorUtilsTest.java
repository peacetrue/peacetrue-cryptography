package com.github.peacetrue.security;

import com.github.peacetrue.codec.Codec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.github.peacetrue.security.KeyPairGeneratorUtils.KEY_LENGTH_1024;

/**
 * @author peace
 **/
class KeyPairGeneratorUtilsTest {

    @Test
    void printRsaKeyPair() {
        Assertions.assertDoesNotThrow(() -> KeyPairGeneratorUtils.printRsaKeyPair(KEY_LENGTH_1024, Codec.HEX));
    }
}
