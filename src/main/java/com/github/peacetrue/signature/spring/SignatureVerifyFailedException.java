package com.github.peacetrue.signature.spring;

import com.github.peacetrue.beans.signedbean.SignedBean;
import lombok.Getter;

/**
 * @author peace
 **/
@Getter
public class SignatureVerifyFailedException extends RuntimeException {

    private final transient SignedBean signedBean;

    public SignatureVerifyFailedException(SignedBean signedBean) {
        super("signature verify failed: " + signedBean);
        this.signedBean = signedBean;
    }

}
