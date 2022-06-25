package com.github.peacetrue.signature;

/**
 * 签名者工厂。
 *
 * @author peace
 **/
public interface SignerFactory<T, S, U> {

    /**
     * 创建一个签名者。
     *
     * @param secret 秘钥
     * @return 签名者
     */
    Signer<T, S> createSigner(U secret);
}
