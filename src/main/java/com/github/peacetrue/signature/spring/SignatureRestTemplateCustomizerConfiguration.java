package com.github.peacetrue.signature.spring;

import com.github.peacetrue.signature.BeanSigner;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author peace
 **/
@Configuration
@AutoConfigureAfter(SignatureAutoConfiguration.class)
@ConditionalOnBean(BeanSigner.class)
@ConditionalOnProperty(name = "peacetrue.signature.sign-path-patterns")
@ConditionalOnClass(name = "org.springframework.boot.web.client.RestTemplateCustomizer")
public class SignatureRestTemplateCustomizerConfiguration {

    @Bean
    public SignatureClientHttpRequestInterceptor signatureClientHttpRequestInterceptor(BeanSigner beanSigner) {
        return new SignatureClientHttpRequestInterceptor(beanSigner);
    }

    @Bean
    public RestTemplateCustomizer restTemplateCustomizer(SignatureClientHttpRequestInterceptor interceptor,
                                                         SignatureProperties properties) {
        return restTemplate -> restTemplate.getInterceptors().add(
                new PathMatcherClientHttpRequestInterceptor(
                        interceptor, properties.getSignPathPatterns()
                )
        );
    }
}
