package com.example.productservice_proxy.clients.authentication.dtos;

import com.example.productservice_proxy.clients.authentication.models.SessionStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateTokenResponseDto {
    private UserDto userDto;
    private SessionStatus sessionStatus;
}
