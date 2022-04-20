package com.dmdev.spring.dto;

import com.dmdev.spring.database.entity.Role;
import lombok.Value;

import java.time.LocalDate;

@Value
public class UserReadDto {
    Long id;
    String username;
    String firstname;
    String lastname;
    LocalDate birthDate;
    Role role;
    CompanyReadDto companyReadDto;
}
