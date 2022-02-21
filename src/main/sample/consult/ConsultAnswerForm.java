package com.sample.sample.consult;

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
public class ConsultAnswerForm {

    private Long id;

    @NotBlank
    @Length(min = 5, max = 400)
    private String answerTitle;

    @NotBlank
    @Length(min = 10)
    private String answerText;

}