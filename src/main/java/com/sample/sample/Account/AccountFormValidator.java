package com.sample.sample.account;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AccountFormValidator implements Validator{
    private final AccountRepositroy accountRepository;

    @Override
    public boolean supports(Class<?> clazz){
        return clazz.isAssignableFrom(AccountForm.class);
    }

    @Override
    public void validate(Object target, Errors errors){
    }
}
