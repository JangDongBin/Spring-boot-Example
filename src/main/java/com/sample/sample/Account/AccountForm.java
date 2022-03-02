package com.sample.sample.account;

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
    private String useridField;
    private String passwordField;
    private String nameField;
    private String emailField;
}