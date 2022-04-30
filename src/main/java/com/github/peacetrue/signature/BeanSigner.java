package com.github.peacetrue.signature;

import com.github.peacetrue.beans.signedbean.SignedBean;
import com.github.peacetrue.beans.tobesignedbean.ToBeSignedBean;
import com.github.peacetrue.lang.UncheckedException;

/**
 * 对象签名者，基于 {@link ToBeSignedBean} 和 {@link SignedBean} 生成签名和验证签名。
 *
 * @author peace
 **/
public interface BeanSigner extends Signer<ToBeSignedBean, SignedBean> {

    /**
     * 生成已签名对象。
     *
     * @return 已签名对象
     */
    default SignedBean generate() throws UncheckedException {
        return sign(SignedBeanImpl.random());
    }

    @Override
    SignedBean sign(ToBeSignedBean toBeSigned) throws UncheckedException;

    @Override
    default boolean verify(ToBeSignedBean toBeSigned, SignedBean signed) throws UncheckedException {
        return verify(signed);
    }

    /**
     * 验证签名信息。
     *
     * @param signed 已签名消息
     * @return true 如果验证通过，否则 false
     */
    boolean verify(SignedBean signed) throws UncheckedException;

}
