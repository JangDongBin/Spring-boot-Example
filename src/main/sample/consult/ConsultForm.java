package com.sample.sample.consult;

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
public class ConsultForm {

    private Long id;

    @NotBlank
    @Length(min = 5, max = 30)
    @Pattern(regexp = "^[a-z0-9_-]{3,20}$")
    private String userid;

    @NotBlank
    @Length(min = 5, max = 400)
    private String consultTitle;

    @NotBlank
    @Length(min = 10)
    private String consultText;

}