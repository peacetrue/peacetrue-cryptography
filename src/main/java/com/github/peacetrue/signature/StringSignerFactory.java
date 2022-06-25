package com.github.peacetrue.signature;

/**
 * 字符串签名者工厂。
 *
 * @author peace
 **/
public interface StringSignerFactory extends SignerFactory<String, String, String> {

    @Override
    StringSigner createSigner(String secret);

}
