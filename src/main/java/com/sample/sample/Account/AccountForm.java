package com.sample.sample.account;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountForm {
    private Long id;

    @NotBlank
    @Length(min = 3, max = 30)
    @Pattern(regexp = "^[a-z0-9_-]{3,20}$")
    private String useridField; //길이는 3~30까지, 특수문자 제외

    @NotBlank
    @Length(min = 3, max = 20)
    private String passwordField; //길이 3~20까지

    @NotBlank
    private String nameField;

    @NotBlank
    @Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*" + "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String emailField;

    private String telField;
    
    
}