package com.github.peacetrue.signature.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author peace
 **/
@Configuration
@ImportAutoConfiguration(classes = {
        SignatureTestController.class,
        HttpMessageConvertersAutoConfiguration.class,
        DispatcherServletAutoConfiguration.class,
        WebMvcAutoConfiguration.class,
        ServletWebServerFactoryAutoConfiguration.class,
        SignatureRestTemplateCustomizerConfiguration.class
})
class SignatureTestConfiguration {

    @Bean
    @Autowired(required = false)
    public RestTemplateBuilder restTemplateBuilder(@Nullable List<RestTemplateCustomizer> restTemplateCustomizers) {
        if (restTemplateCustomizers == null) restTemplateCustomizers = Collections.emptyList();
        return new RestTemplateBuilder(restTemplateCustomizers.toArray(new RestTemplateCustomizer[0]));
    }

}
