package com.github.peacetrue.signature.spring;

import com.github.peacetrue.signature.BeanSigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author peace
 **/
@Configuration
@AutoConfigureAfter(SignatureAutoConfiguration.class)
@ConditionalOnBean(BeanSigner.class)
@ConditionalOnProperty(name = "peacetrue.signature.verify-path-patterns")
public class SignatureWebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private SignatureProperties properties;
    @Autowired
    private SignatureHandlerInterceptor signatureHandlerInterceptor;

    @Bean
    public SignatureHandlerInterceptor signatureHandlerInterceptor(BeanSigner beanSigner) {
        return new SignatureHandlerInterceptor(beanSigner);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] patterns = properties.getVerifyPathPatterns().toArray(new String[0]);
        registry.addInterceptor(signatureHandlerInterceptor).addPathPatterns(patterns);
    }
}
