package com.example.second_service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/second-service")
@AllArgsConstructor
public class Controller {

    @GetMapping()
    public String CircuitBreakerFailCase() throws Exception {
        log.info("CircuitBreakerFailCase started");
        // return exception
        throw new Exception("CircuitBreakerFailCase");
    }

    @GetMapping("/welcome")
    public String welcome() {
        log.info("CircuitBreakerFailCase started");
        return "welcome";

    }
}
