package com.github.peacetrue.signature;

import com.github.peacetrue.codec.Codec;
import com.github.peacetrue.lang.UncheckedException;
import com.github.peacetrue.security.KeyPairGeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static com.github.peacetrue.security.KeyPairGeneratorUtils.KEY_LENGTH_1024;

/**
 * @author peace
 **/
@Slf4j
class SignerTest {

    @Test
    void sign() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new AsymmetricSigner(null, null));

        KeyPair keyPair = KeyPairGeneratorUtils.generateRsaKeyPair(KEY_LENGTH_1024);
        AsymmetricSigner errorAsymmetricSigner = new AsymmetricSigner("error algorithm", keyPair.getPrivate(), keyPair.getPublic());
        Assertions.assertThrows(UncheckedException.class, () -> errorAsymmetricSigner.sign(new byte[0]));
        Assertions.assertThrows(UncheckedException.class, () -> errorAsymmetricSigner.verify(new byte[0], new byte[0]));

        String toBeSignedString = RandomStringUtils.randomAlphanumeric(10);
        CodecSignerFactory codecSignerFactory = new CodecSignerFactory(BytesSignerFactory.HmacSHA256, Codec.CHARSET_UTF8);
        StringSigner codecSigner = codecSignerFactory.createSigner("12");
        Assertions.assertTrue(codecSigner.verify(toBeSignedString, codecSigner.sign(toBeSignedString)));

        StringSigner stringSigner = toBeSigned1 -> toBeSigned1;
        Assertions.assertTrue(stringSigner.verify(toBeSignedString, stringSigner.sign(toBeSignedString)));

        byte[] toBeSigned = Codec.CHARSET_UTF8.decode(toBeSignedString);
        BytesSigner signer = BytesSignerFactory.HmacSHA256.createSigner(toBeSigned);
        Assertions.assertTrue(signer.verify(toBeSigned, signer.sign(toBeSigned)));

        BytesSigner signerPrivate = BytesSignerFactory.SHA256WithRSA_PRIVATE.createSigner(keyPair.getPrivate().getEncoded());
        BytesSigner signerPublic = BytesSignerFactory.SHA256WithRSA_PUBLIC.createSigner(keyPair.getPublic().getEncoded());
        Assertions.assertTrue(signerPublic.verify(toBeSigned, signerPrivate.sign(toBeSigned)));

        BytesSigner bytesSigner = toBeSigned1 -> toBeSigned1;
        Assertions.assertTrue(bytesSigner.verify(toBeSigned, bytesSigner.sign(toBeSigned)));
    }


}
