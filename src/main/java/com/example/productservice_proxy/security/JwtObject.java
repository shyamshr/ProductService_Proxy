package com.example.productservice_proxy.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtObject {
    private String email;
    private String role;
    private String token;

}
