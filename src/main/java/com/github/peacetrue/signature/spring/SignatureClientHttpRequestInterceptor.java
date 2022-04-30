package com.github.peacetrue.signature.spring;

import com.github.peacetrue.beans.signedbean.SignedBean;
import com.github.peacetrue.lang.UncheckedException;
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
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
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
    public ClientHttpResponse intercept(HttpRequest request, byte[] bytes, ClientHttpRequestExecution execution) throws IOException {
        URI uri = rebuildURI(request.getURI());
        log.debug("signed request uri: {}", uri);
        return execution.execute(new HttpRequestWrapper(request) {
            @Override
            public URI getURI() {
                return uri;
            }
        }, bytes);
    }

    private URI rebuildURI(URI uri) {
        SignedBean signedBean = beanSigner.generate();
        log.debug("generate SignedBean: {}", signedBean);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        BeanUtils.getPropertyValues(signedBean).forEach((key, value) -> params.add(key, encode(value)));
        return UriComponentsBuilder.fromUri(uri).queryParams(params).build(true).toUri();
    }

    private static String encode(Object value) {
        try {
            return URLEncoder.encode(String.valueOf(value), "utf8");
        } catch (UnsupportedEncodingException e) {
            throw new UncheckedException(e);
        }
    }

}
