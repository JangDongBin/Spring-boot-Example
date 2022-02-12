package com.sample.sample.board;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordForm {
    private Long id;

    @NotBlank
    @Length(min=1, max = 10)
    private String PasswordFiled;
}
