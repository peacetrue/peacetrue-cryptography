package com.github.peacetrue.signature;

import com.github.peacetrue.digest.HmacDigester;
import com.github.peacetrue.security.KeyFactoryUtils;

/**
 * 字节数组签名者工厂。
 *
 * @author peace
 **/
public interface BytesSignerFactory extends SignerFactory<byte[], byte[], byte[]> {

    BytesSignerFactory HmacSHA256 = secret -> new DigestSigner(HmacDigester.buildHmacSHA256(secret));
    BytesSignerFactory SHA256WithRSA_PUBLIC = secret -> new AsymmetricSigner(null, KeyFactoryUtils.generateRsaPublic(secret));
    BytesSignerFactory SHA256WithRSA_PRIVATE = secret -> new AsymmetricSigner(KeyFactoryUtils.generateRsaPrivate(secret), null);

    @Override
    BytesSigner createSigner(byte[] secret);
}
