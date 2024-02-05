package com.example.productservice_proxy.clients.authentication.dtos;

import com.example.productservice_proxy.clients.authentication.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
public class UserDto {
    private String email;
    private Set<Role> roles = new HashSet<>();

}
