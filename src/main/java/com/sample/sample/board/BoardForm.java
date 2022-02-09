package com.sample.sample.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardForm {
    
    private Long id;

    private String userid;

    private String Title;

    private String Text;

    private String Password;
    
}
