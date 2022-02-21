package com.sample.sample.consult;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConsultAnswerFormValidator  implements Validator {

    private final ConsultRepository consultRepository;

    //사용자로부터 입력받은 클래스가 인터페이스를 (implements)구현한 클래스인지 체크하는 것
    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return clazz.isAssignableFrom(ConsultAnswerForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // TODO Auto-generated method stub
        // ConsultForm consultForm = (ConsultForm) target;
    }


    
}