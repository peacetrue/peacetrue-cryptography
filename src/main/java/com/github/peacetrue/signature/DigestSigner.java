package com.github.peacetrue.signature;

import com.github.peacetrue.digest.Digester;
import com.github.peacetrue.digest.HmacDigester;

import java.util.Objects;

/**
 * 摘要签名者，配合 {@link HmacDigester} 使用。
 *
 * @author peace
 * @see HmacDigester
 **/
public class DigestSigner implements Signer<byte[], byte[]> {

    private final Digester<byte[]> digester;

    public DigestSigner(Digester<byte[]> digester) {
        this.digester = Objects.requireNonNull(digester);
    }

    @Override
    public byte[] sign(byte[] toBeSigned) {
        return digester.digest(toBeSigned);
    }

    @Override
    public boolean verify(byte[] toBeSigned, byte[] signed) {
        return digester.match(toBeSigned, signed);
    }

}
