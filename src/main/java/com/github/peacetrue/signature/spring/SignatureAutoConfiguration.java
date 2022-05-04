package com.github.peacetrue.signature.spring;

import com.github.peacetrue.codec.Codec;
import com.github.peacetrue.digest.HmacDigester;
import com.github.peacetrue.security.KeyFactoryUtils;
import com.github.peacetrue.signature.*;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * 签名自动配置。
 *
 * @author peace
 **/
@Configuration
@EnableConfigurationProperties(SignatureProperties.class)
@ImportAutoConfiguration({SignatureRestTemplateCustomizerConfiguration.class, SignatureWebConfiguration.class})
public class SignatureAutoConfiguration {

    private final SignatureProperties properties;

    public SignatureAutoConfiguration(SignatureProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(name = "keyCodec")
    public Codec keyCodec() {
        return Codec.HEX;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnExpression("'${peacetrue.signature.secretKey:}'!='' or '${peacetrue.signature.privateKey:}'!='' or '${peacetrue.signature.publicKey:}'!=''")
    public BeanSigner beanSigner(Codec keyCodec) {
        Signer<byte[], byte[]> signer = properties.getSecretKey() == null
                ? getStandardSigner(keyCodec, properties.getPublicKey(), properties.getPrivateKey())
                : getDigestSigner(keyCodec, properties.getSecretKey());
        return new SimpleBeanSigner(new StringSigner(signer));
    }

    private Signer<byte[], byte[]> getDigestSigner(Codec keyCodec, String secretKey) {
        return new DigestSigner(HmacDigester.buildHmacSHA256(keyCodec.decode(secretKey)));
    }

    private StandardSigner getStandardSigner(Codec keyCodec, @Nullable String publicKey, @Nullable String privateKey) {
        return new StandardSigner(
                Optional.ofNullable(publicKey)
                        .map(keyCodec::decode)
                        .map(KeyFactoryUtils::generateRsaPublic)
                        .orElse(null),
                Optional.ofNullable(privateKey)
                        .map(keyCodec::decode)
                        .map(KeyFactoryUtils::generateRsaPrivate)
                        .orElse(null)
        );
    }


}
