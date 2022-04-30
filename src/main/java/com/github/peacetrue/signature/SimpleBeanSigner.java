package com.github.peacetrue.signature;

import com.github.peacetrue.beans.signedbean.SignedBean;
import com.github.peacetrue.beans.tobesignedbean.ToBeSignedBean;

import java.util.Objects;

/**
 * 简单对象签名者。
 *
 * @author peace
 **/
public class SimpleBeanSigner implements BeanSigner {

    private final Signer<String, String> signer;

    public SimpleBeanSigner(Signer<String, String> signer) {
        this.signer = Objects.requireNonNull(signer);
    }

    @Override
    public SignedBean sign(ToBeSignedBean toBeSigned) {
        String signature = signer.sign(toString(toBeSigned));
        return SignedBeanImpl.build(toBeSigned, signature);
    }

    @Override
    public boolean verify(SignedBean signed) {
        return signer.verify(toString(signed), signed.getSignature());
    }

    /**
     * 转换待签名对象为字符串。
     *
     * @param toBeSigned 待签名对象
     * @return 待签名字符串
     */
    protected String toString(ToBeSignedBean toBeSigned) {
        return toBeSigned.getNonce() + toBeSigned.getTimestamp();
    }
}
