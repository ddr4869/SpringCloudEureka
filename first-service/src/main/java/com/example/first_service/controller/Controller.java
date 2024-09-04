package com.example.first_service.controller;

import com.example.first_service.kafka.KafkaProducerController;
import com.example.first_service.service.FirstService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/first-service")
@AllArgsConstructor
public class Controller {

    private final Environment env;
    private final KafkaProducerController kafkaProducerController;
    private final FirstService firstService;


    @GetMapping("/welcome")
    public String welcome() {
        return String.format("Welcome to the First Service, Port is %s",
                 env.getProperty("token.secret"));
    }

    @GetMapping("/message")
    public String message(@RequestHeader("first-request") String header) {
        log.info(header);
        return "Welcome to the first service.";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        log.info("Server port = {}", request.getServerPort());
        return String.format("This is a message from First Service on PORT %s",
                8081);
    }
    @PostMapping("/kafka")
    public void kafka() {
        kafkaProducerController.sendMassage("Hello Kafka");
    }

    @PostMapping("/circuit")
    public Object circuit(HttpServletRequest request) {
        log.info("circuit started");
        return firstService.getFeignCall();
    }
}