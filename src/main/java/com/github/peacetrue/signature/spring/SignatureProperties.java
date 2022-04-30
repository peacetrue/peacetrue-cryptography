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

    /** 私钥，使用 SHA256WithRSA 算法，生产签名时的私钥，默认使用 HEX 解码 */
    private String privateKey;
    /** 公钥，使用 SHA256WithRSA 算法，验证签名时的公钥，默认使用 HEX 解码 */
    private String publicKey;
    /** 密钥，使用 HmacSHA256 算法，生成和验证签名时的密钥，默认使用 HEX 解码 */
    private String secretKey;
    /** 签名路径规则，生成签名时，拦截请求的路径规则，不配置此项不开启签名生成，拦截所有可配置为：/** */
    private List<String> signPathPatterns;
    /** 验签路径规则，验证签名时，拦截请求的路径规则，不配置此项不开启签名验证，拦截所有可配置为：/** */
    private List<String> verifyPathPatterns;

}
