package com.github.peacetrue.security;

import com.github.peacetrue.codec.Codec;
import com.github.peacetrue.codec.HexCodec;
import com.github.peacetrue.lang.UncheckedException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.security.KeyPair;

import static com.github.peacetrue.security.KeyPairGeneratorUtils.KEY_LENGTH_1024;

/**
 * @author peace
 **/
@Slf4j
class KeyPairGeneratorUtilsTest {

    @Test
    void getKeyFactory() {
        Assertions.assertThrows(UncheckedException.class, () -> KeyFactoryUtils.getKeyFactory("RSE"));
    }

    @Test
    void getKeyPairGenerator() {
        Assertions.assertThrows(UncheckedException.class, () -> KeyPairGeneratorUtils.getKeyPairGenerator("RSE"));
    }

    @Test
    void generateRsaPrivate() {
        Assertions.assertThrows(UncheckedException.class, () -> KeyFactoryUtils.generateRsaPrivate(Codec.CHARSET_UTF8.decode("1")));
    }

    @Test
    void generateRsaPublic() {
        Assertions.assertThrows(UncheckedException.class, () -> KeyFactoryUtils.generateRsaPublic(Codec.CHARSET_UTF8.decode("1")));
    }

    @Test
    void printRsaKeyPair() {
        Assertions.assertDoesNotThrow(() -> printRsaKeyPair(KEY_LENGTH_1024, HexCodec.DEFAULT));
    }

    public static void printRsaKeyPair(int keyLength, Codec codec) {
        KeyPair keyPair = KeyPairGeneratorUtils.generateRsaKeyPair(keyLength);
        log.info("PublicKey: {}", toString(keyPair.getPublic(), codec));
        log.info("PrivateKey: {}", toString(keyPair.getPrivate(), codec));
    }

    public static String toString(Key key, Codec codec) {
        return codec.encode(key.getEncoded());
    }
}
