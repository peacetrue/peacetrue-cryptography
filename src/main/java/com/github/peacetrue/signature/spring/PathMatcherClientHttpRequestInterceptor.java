package com.github.peacetrue.signature.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * 支持路径匹配。
 *
 * @author peace
 **/
@Slf4j
public class PathMatcherClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private final PathMatcher pathMatcher;
    private final ClientHttpRequestInterceptor interceptor;
    private final List<String> pathPatterns;

    public PathMatcherClientHttpRequestInterceptor(ClientHttpRequestInterceptor interceptor, List<String> pathPatterns) {
        this(new AntPathMatcher(), interceptor, pathPatterns);
    }

    public PathMatcherClientHttpRequestInterceptor(PathMatcher pathMatcher, ClientHttpRequestInterceptor interceptor, List<String> pathPatterns) {
        this.pathMatcher = Objects.requireNonNull(pathMatcher);
        this.interceptor = Objects.requireNonNull(interceptor);
        this.pathPatterns = Objects.requireNonNull(pathPatterns);
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        boolean matched = match(request);
        log.debug("matched request: {}", matched);
        return matched
                ? interceptor.intercept(request, body, execution)
                : execution.execute(request, body);
    }

    protected boolean match(HttpRequest request) {
        return match(request.getURI().getPath());
    }

    protected boolean match(String uriPath) {
        log.debug("try to match request uri path: {}", uriPath);
        return pathPatterns.stream().anyMatch(item -> pathMatcher.match(item, uriPath));
    }
}
