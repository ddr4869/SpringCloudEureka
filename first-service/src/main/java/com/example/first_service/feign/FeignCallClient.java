package com.example.first_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "feign", url = "http://localhost:8080/second-service")
public interface FeignCallClient {
    @RequestMapping(method = RequestMethod.GET, value="/")
    List<Object> getException();
}
