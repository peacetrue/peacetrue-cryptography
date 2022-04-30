package com.github.peacetrue.signature.spring;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author peace
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SignatureTestConfiguration.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class SignatureSpringTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void echo() {
        String input = RandomStringUtils.randomAlphanumeric(10);
        String output = this.restTemplate.getForObject("/echo?input={0}", String.class, input);
        Assertions.assertEquals(input, output, "测试一般情况下 /echo 接口可以正常调用");
    }

    @ExtendWith(SpringExtension.class)
    @SpringBootTest(classes = {SignatureAutoConfiguration.class, SignatureTestConfiguration.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
    @ActiveProfiles("correctPublicPrivateKey")
    static class CorrectPublicPrivateKey {

        @Autowired
        private TestRestTemplate restTemplate;

        @Test
        void echo() {
            String input = RandomStringUtils.randomAlphanumeric(10);
            String output = this.restTemplate.getForObject("/echo?input={0}", String.class, input);
            Assertions.assertEquals(input, output);
        }
    }

    @ExtendWith(SpringExtension.class)
    @SpringBootTest(classes = {SignatureAutoConfiguration.class, SignatureTestConfiguration.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
    @ActiveProfiles("errorPublicPrivateKey")
    static class ErrorPublicPrivateKey {

        @Autowired
        private TestRestTemplate restTemplate;

        @Test
        void echo() {
            String input = RandomStringUtils.randomAlphanumeric(10);
            ResponseEntity<String> output = this.restTemplate.getForEntity("/echo?input={0}", String.class, input);
            Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, output.getStatusCode());
        }
    }

    @ExtendWith(SpringExtension.class)
    @SpringBootTest(classes = {SignatureAutoConfiguration.class, SignatureTestConfiguration.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
    @ActiveProfiles("secretKey")
    static class CorrectSecretKey {

        @Autowired
        private TestRestTemplate restTemplate;

        @Test
        void echo() {
            String input = RandomStringUtils.randomAlphanumeric(10);
            String output = this.restTemplate.getForObject("/echo?input={0}", String.class, input);
            Assertions.assertEquals(input, output);
        }
    }


}
