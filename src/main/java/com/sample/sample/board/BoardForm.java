package com.sample.sample.board;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardForm {
    
    private Long id;
    @NotBlank
    @Length(min = 3, max = 30)
    @Pattern(regexp = "^[a-z0-9_-]{3,20}$")
    private String useridField;

    @NotBlank
    @Length(min = 3, max = 20)
    private String TitleField;

    @NotBlank
    @Length(min = 5)
    private String TextField;

    @NotBlank
    @Length(min = 1, max = 10)
    private String PasswordField;
    
}
