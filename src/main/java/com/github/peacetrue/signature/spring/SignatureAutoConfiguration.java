package com.github.peacetrue.signature.spring;

import com.github.peacetrue.codec.Codec;
import com.github.peacetrue.digest.HmacDigester;
import com.github.peacetrue.security.KeyFactoryUtils;
import com.github.peacetrue.signature.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Objects;
import java.util.Optional;

/**
 * 签名自动配置。
 *
 * @author peace
 **/
@Configuration
@EnableConfigurationProperties(SignatureProperties.class)
public class SignatureAutoConfiguration {

    private final SignatureProperties properties;

    public SignatureAutoConfiguration(SignatureProperties properties) {
        this.properties = Objects.requireNonNull(properties);
    }

    @Bean
    @ConditionalOnMissingBean(name = "keyCodec")
    public Codec keyCodec() {
        return Codec.HEX;
    }

    @Bean
    @ConditionalOnMissingBean
    public BeanSigner beanSigner(Codec keyCodec) {
        Signer<byte[], byte[]> signer = properties.getSecretKey() == null
                ? getStandardSigner(keyCodec)
                : getDigestSigner(keyCodec);
        return new SimpleBeanSigner(new StringSigner(signer));
    }

    private Signer<byte[], byte[]> getDigestSigner(Codec keyCodec) {
        return new DigestSigner(HmacDigester.buildHmacSHA256(keyCodec.decode(properties.getSecretKey())));
    }

    private StandardSigner getStandardSigner(Codec keyCodec) {
        return new StandardSigner(
                Optional.ofNullable(properties.getPublicKey())
                        .map(keyCodec::decode)
                        .map(KeyFactoryUtils::generateRsaPublic)
                        .orElse(null),
                Optional.ofNullable(properties.getPrivateKey())
                        .map(keyCodec::decode)
                        .map(KeyFactoryUtils::generateRsaPrivate)
                        .orElse(null)
        );
    }

    @Bean
    public SignatureClientHttpRequestInterceptor signatureClientHttpRequestInterceptor(BeanSigner beanSigner) {
        return new SignatureClientHttpRequestInterceptor(beanSigner);
    }

    @Bean
    public SignatureHandlerInterceptor signatureHandlerInterceptor(BeanSigner beanSigner) {
        return new SignatureHandlerInterceptor(beanSigner);
    }

    @Configuration
    @AutoConfigureAfter(SignatureAutoConfiguration.class)
    @ConditionalOnClass(name = "org.springframework.boot.web.client.RestTemplateCustomizer")
    @ConditionalOnProperty(name = "peacetrue.signature.sign-path-patterns")
    public static class SignatureRestTemplateCustomizerConfiguration {

        @Autowired
        private SignatureProperties properties;
        @Autowired
        private SignatureClientHttpRequestInterceptor signatureClientHttpRequestInterceptor;

        @Bean
        public RestTemplateCustomizer restTemplateCustomizer() {
            return restTemplate -> restTemplate.getInterceptors().add(
                    new PathMatcherClientHttpRequestInterceptor(
                            signatureClientHttpRequestInterceptor, properties.getSignPathPatterns()
                    )
            );
        }
    }

    @Configuration
    @AutoConfigureAfter(SignatureAutoConfiguration.class)
    @ConditionalOnProperty(name = "peacetrue.signature.verify-path-patterns")
    public static class SignatureWebConfiguration extends WebMvcConfigurerAdapter {

        @Autowired
        private SignatureHandlerInterceptor signatureHandlerInterceptor;
        @Autowired
        private SignatureProperties properties;

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            String[] patterns = properties.getVerifyPathPatterns().toArray(new String[0]);
            registry.addInterceptor(signatureHandlerInterceptor).addPathPatterns(patterns);
        }
    }

}
