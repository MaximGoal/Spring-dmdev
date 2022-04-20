package com.dmdev.spring.validation.impl;

import com.dmdev.spring.database.repository.CompanyRepository;
import com.dmdev.spring.dto.UserCreateEditDto;
import com.dmdev.spring.validation.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

// all ConstraintValidators are Spring beans
@RequiredArgsConstructor
public class  UserInfoValidator implements ConstraintValidator<UserInfo, UserCreateEditDto> {

    private final CompanyRepository companyRepository;

    @Override
    public boolean isValid(UserCreateEditDto value, ConstraintValidatorContext context) {
        return StringUtils.hasText(value.getFirstname()) || StringUtils.hasText(value.getLastname());
    }
}
