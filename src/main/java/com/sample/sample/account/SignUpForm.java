package com.sample.sample.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpForm {

    private String userid;
    private String password;
    private String name;
    private String emailAddress;
}