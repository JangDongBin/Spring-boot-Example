package com.sample.sample.Account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("회원가입 - 정상")
    @Test
    public void SignupForm_corrent() throws Exception{

        mockMvc.perform(post("/account/add")
                .param("useridField", "test")
                .param("passwordField", "1111")
                .param("nameField", "엄복동")
                .param("emailField", "jangbayooffcial@gmail.com")
                .with(csrf())
        )
                .andExpect(status().is3xxRedirection());

    }

}
