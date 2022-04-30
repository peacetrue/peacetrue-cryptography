package com.github.peacetrue.signature.spring;

import com.github.peacetrue.beans.properties.nonce.NonceCapable;
import com.github.peacetrue.beans.properties.signature.SignatureCapable;
import com.github.peacetrue.beans.properties.timestamp.TimestampCapable;
import com.github.peacetrue.signature.BeanSigner;
import com.github.peacetrue.signature.SignedBeanImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 签名验证拦截器。
 *
 * @author peace
 **/
@Slf4j
public class SignatureHandlerInterceptor extends HandlerInterceptorAdapter {

    private final BeanSigner beanSigner;

    public SignatureHandlerInterceptor(BeanSigner beanSigner) {
        this.beanSigner = Objects.requireNonNull(beanSigner);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SignedBeanImpl signedBean = new SignedBeanImpl();
        signedBean.setNonce(getParameter(request, NonceCapable.PROPERTY_NONCE));
        signedBean.setTimestamp(getLongParameter(request, TimestampCapable.PROPERTY_TIMESTAMP));
        signedBean.setSignature(getParameter(request, SignatureCapable.PROPERTY_SIGNATURE));
        log.debug("got SignedBean: {}", signedBean);
        boolean passed = beanSigner.verify(signedBean);
        log.debug("signature verify passed: {}", passed);
        if (!passed) throw new SignatureVerifyFailedException(signedBean);
        return super.preHandle(request, response, handler);
    }

    private static Long getLongParameter(HttpServletRequest request, String name) throws MissingServletRequestParameterException {
        String timestamp = getParameter(request, name);
        try {
            return Long.parseLong(timestamp);
        } catch (NumberFormatException e) {
            TypeMismatchException typeMismatchException = new TypeMismatchException(timestamp, Long.class, e);
            typeMismatchException.initPropertyName(name);
            throw typeMismatchException;
        }
    }

    private static String getParameter(HttpServletRequest request, String name) throws MissingServletRequestParameterException {
        String value = request.getParameter(name);
        if (value != null) return value;
        throw new MissingServletRequestParameterException(name, String.class.getName());
    }

}
