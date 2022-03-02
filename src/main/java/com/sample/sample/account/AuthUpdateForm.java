package com.sample.sample.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUpdateForm {
    private Boolean Role_TEMP_USER;
    private Boolean Role_USER;
    private Boolean Role_ADMIN;
    
}
