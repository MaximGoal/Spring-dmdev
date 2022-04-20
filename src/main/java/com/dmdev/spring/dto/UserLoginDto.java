package com.dmdev.spring.dto;

import lombok.Value;

@Value
public class UserLoginDto {
    private String username;
    private String password;
}
