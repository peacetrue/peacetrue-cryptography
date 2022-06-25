package com.github.peacetrue.signature;

import com.github.peacetrue.lang.UncheckedException;

/**
 * 字符串签名者。
 *
 * @author peace
 **/
public interface StringSigner extends Signer<String, String> {

    @Override
    String sign(String toBeSigned) throws UncheckedException;

    @Override
    default boolean verify(String toBeSigned, String signed) throws UncheckedException {
        return Signer.super.verify(toBeSigned, signed);
    }
}
