package com.github.peacetrue.signature;

import com.github.peacetrue.lang.UncheckedException;

import java.util.Arrays;

/**
 * 字节数组签名者。
 *
 * @author peace
 **/
public interface BytesSigner extends Signer<byte[], byte[]> {

    @Override
    byte[] sign(byte[] toBeSigned) throws UncheckedException;

    @Override
    default boolean verify(byte[] toBeSigned, byte[] signed) throws UncheckedException {
        return Arrays.equals(signed, sign(toBeSigned));
    }

}
