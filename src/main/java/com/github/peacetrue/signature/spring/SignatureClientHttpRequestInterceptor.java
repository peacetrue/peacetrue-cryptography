package com.github.peacetrue.signature.spring;

import com.github.peacetrue.beans.signedbean.SignedBean;
import com.github.peacetrue.net.URLCodecUtils;
import com.github.peacetrue.signature.BeanSigner;
import com.github.peacetrue.spring.beans.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;

/**
 * 发送请求时添加签名参数。
 *
 * @author peace
 */
@Slf4j
public class SignatureClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private final BeanSigner beanSigner;

    public SignatureClientHttpRequestInterceptor(BeanSigner beanSigner) {
        this.beanSigner = Objects.requireNonNull(beanSigner);
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        URI uri = rebuildURI(request.getURI());
        log.debug("signed request uri: {}", uri);
        return execution.execute(new HttpRequestWrapper(request) {
            @Override
            public URI getURI() {
                return uri;
            }
        }, body);
    }

    private URI rebuildURI(URI uri) {
        SignedBean signedBean = beanSigner.generate();
        log.debug("generate SignedBean: {}", signedBean);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        BeanUtils.getPropertyValues(signedBean).forEach((key, value) -> params.add(key, URLCodecUtils.encode(String.valueOf(value))));
        return UriComponentsBuilder.fromUri(uri).queryParams(params).build(true).toUri();
    }


}
