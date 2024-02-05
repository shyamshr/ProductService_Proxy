package com.example.productservice_proxy.clients.authentication;

import com.example.productservice_proxy.clients.authentication.dtos.ValidateTokenRequestDto;
import com.example.productservice_proxy.clients.authentication.dtos.ValidateTokenResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationClient {
    private RestTemplateBuilder restTemplateBuilder;
    @Autowired
    public AuthenticationClient(RestTemplateBuilder restTemplateBuilder){
        this.restTemplateBuilder = restTemplateBuilder;
    }
    public ValidateTokenResponseDto validate(ValidateTokenRequestDto validateTokenRequestDto){
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate.postForObject("http://localhost:9000/auth/validate", validateTokenRequestDto, ValidateTokenResponseDto.class);
    }
}
