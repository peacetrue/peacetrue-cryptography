package com.github.peacetrue.signature.spring;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author peace
 */
@RestController
@RequestMapping
class SignatureTestController {

    @ResponseBody
    @RequestMapping(value = "/echo", produces = MediaType.APPLICATION_JSON_VALUE)
    public String echo(String input) {
        return input;
    }

}
