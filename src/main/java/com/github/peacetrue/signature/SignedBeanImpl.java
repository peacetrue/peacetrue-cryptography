package com.github.peacetrue.signature;

import com.github.peacetrue.beans.signedbean.SignedBean;
import com.github.peacetrue.beans.tobesignedbean.ToBeSignedBean;
import com.github.peacetrue.spring.beans.BeanUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

/**
 * @author peace
 **/
@Getter
@Setter
@ToString
public class SignedBeanImpl implements SignedBean {

    private Long timestamp;
    private String nonce;
    private String signature;

    /**
     * 随机生成一个唯一的待签名对象。
     *
     * @return 待签名对象
     */
    public static ToBeSignedBean random() {
        SignedBeanImpl signedBean = new SignedBeanImpl();
        signedBean.setNonce(UUID.randomUUID().toString());
        signedBean.setTimestamp(System.currentTimeMillis());
        return signedBean;
    }

    /**
     * 从待签名对象和签名构建已签名对象。
     *
     * @param toBeSigned 待签名对象
     * @param signature  签名
     * @return 已签名对象
     */
    public static SignedBean build(ToBeSignedBean toBeSigned, String signature) {
        SignedBean signed = toBeSigned instanceof SignedBean
                ? (SignedBean) toBeSigned
                : BeanUtils.convert(toBeSigned, SignedBeanImpl.class);
        signed.setSignature(signature);
        return signed;
    }

}
