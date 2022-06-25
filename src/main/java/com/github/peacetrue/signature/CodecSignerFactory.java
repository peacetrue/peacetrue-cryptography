package com.github.peacetrue.signature;

import com.github.peacetrue.codec.Codec;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 字符串签名者工厂。
 *
 * @author peace
 **/
public class CodecSignerFactory implements StringSignerFactory {

    private final Map<String, CodecSigner> signers = new ConcurrentHashMap<>();
    private final SignerFactory<byte[], byte[], byte[]> signerFactory;
    private final Codec secretCodec;

    public CodecSignerFactory(SignerFactory<byte[], byte[], byte[]> signerFactory, Codec secretCodec) {
        this.signerFactory = Objects.requireNonNull(signerFactory);
        this.secretCodec = Objects.requireNonNull(secretCodec);
    }

    @Override
    public CodecSigner createSigner(String secret) {
        return signers.computeIfAbsent(secret,
                temp -> new CodecSigner(signerFactory.createSigner(this.secretCodec.decode(temp)))
        );
    }
}
