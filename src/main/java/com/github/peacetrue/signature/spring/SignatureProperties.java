package com.github.peacetrue.signature.spring;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author peace
 **/
@Getter
@Setter
@ToString
@ConfigurationProperties("peacetrue.signature")
public class SignatureProperties {

    private String publicKey;
    private String privateKey;
    private String secretKey;
    private List<String> interceptorPatterns;

}
