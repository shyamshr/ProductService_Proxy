package com.example.productservice_proxy.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class TokenValidator {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private Optional<JwtObject> validateToken(){
        RestTemplate restTemplate = restTemplateBuilder.build();
        JwtObject jwtObject = restTemplate.getForEntity("http://localhost:8080/validate", JwtObject.class).getBody();
        return null;

    }
}
