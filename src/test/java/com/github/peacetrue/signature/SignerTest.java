package com.github.peacetrue.signature;

import com.github.peacetrue.CryptologyUtils;
import com.github.peacetrue.beans.signedbean.SignedBean;
import com.github.peacetrue.digest.HmacDigester;
import com.github.peacetrue.security.KeyPairGeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static com.github.peacetrue.security.KeyPairGeneratorUtils.*;

/**
 * @author peace
 **/
@Slf4j
class SignerTest {

    @Test
    void sign() {
        KeyPair keyPair = KeyPairGeneratorUtils.generateRsaKeyPair(KEY_LENGTH_1024);
        StandardSigner standardSigner = new StandardSigner(keyPair.getPublic(), keyPair.getPrivate());
        StringSigner stringSigner = new StringSigner(standardSigner);
        String toBeSigned = RandomStringUtils.randomAlphanumeric(10);
        String signature = stringSigner.sign(toBeSigned);
        Assertions.assertTrue(stringSigner.verify(toBeSigned, signature));

        BeanSigner beanSigner = new SimpleBeanSigner(new StringSigner(standardSigner));
        SignedBean signedBean = beanSigner.generate();
        Assertions.assertTrue(beanSigner.verify(signedBean));

        beanSigner = new SimpleBeanSigner(new StringSigner(new DigestSigner(HmacDigester.buildHmacSHA256(CryptologyUtils.randomBytes()))));
        signedBean = beanSigner.generate();
        Assertions.assertTrue(beanSigner.verify(signedBean));
    }
}
