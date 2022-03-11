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
// @Transactional //트랜젝션 붙이면 테스트 해보고 롤백됨
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("회원가입 - 정상")
    @Test
    public void SignupForm_pass() throws Exception {
        mockMvc.perform(post("/account/add")
                .param("useridField", "test123")
                .param("passwordField", "1111")
                .param("nameField", "엄복동")
                .param("emailField", "jangbayooffcial@gmail.com")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("회원가입 - 실패")
    @Test
    public void SignupForm_fail() throws Exception {
        mockMvc.perform(post("/account/add")
                .param("useridField", "test")
                .param("passwordField", "")
                .param("nameField", "엄복동")
                .param("emailField", "jangbayooffcial@gmail.com")
                .with(csrf()))
                .andExpect(status().isOk());
    }


    @DisplayName("로그인 - 성공")
    @Test
    public void Login_pass() throws Exception {
        mockMvc.perform(post("/account/login")
                .param("useridField", "test")
                .param("passwordField", "1111")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("로그인 - 실패")
    @Test
    public void Login_fail() throws Exception {
        mockMvc.perform(post("/account/login?error")
                .param("useridField", "test")
                .param("passwordField", "1234")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("권한 업데이트 - 성공")
    @Test
    public void Auth_Update_pass() throws Exception {
        mockMvc.perform(post("/account/update")
                .param("useridField", "test")
                .param("passwordField", "1234")
                .param("auth", "1")
                .param("auth", "2")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("권한 업데이트 - 실패")
    @Test
    public void Auth_Update_fail() throws Exception {
        mockMvc.perform(post("/account/update")
                .param("useridField", "test")
                .param("passwordField", "1234")
                
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}
