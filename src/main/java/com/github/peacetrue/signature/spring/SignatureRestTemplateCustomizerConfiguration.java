package com.github.peacetrue.signature.spring;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author peace
 **/
@Configuration
@AutoConfigureAfter(SignatureAutoConfiguration.class)
@ConditionalOnClass(name = "org.springframework.boot.web.client.RestTemplateCustomizer")
public class SignatureRestTemplateCustomizerConfiguration {
    @Bean
    @ConditionalOnBean(SignatureClientHttpRequestInterceptor.class)
    public RestTemplateCustomizer restTemplateCustomizer(SignatureClientHttpRequestInterceptor signatureClientHttpRequestInterceptor) {
        return restTemplate -> restTemplate.getInterceptors().add(signatureClientHttpRequestInterceptor);
    }
}
