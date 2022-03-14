package com.sample.sample.Account;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import com.sample.sample.account.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    private void beforeEach() {
        
        System.out.println("BEFORE======================");
        AccountForm accountForm = new AccountForm();
        accountForm.setUseridField("test12345");
        accountForm.setPasswordField("1234");
        accountForm.setNameField("test계정");
        accountForm.setEmailField("jangbayo@jangbolggayo.com");
        accountService.updateProcess(accountForm);
        System.out.println("BEFORE======================");
        
    }
    
    @AfterEach
    private void AfterEach(){
        System.out.println("AFTER======================");
    }
    
    @WithUserDetails(value = "test", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("권한 업데이트")
    @Test
    public void Auth_Update_pass() throws Exception {
        String userid = "test12345";
        List<Role> roles = roleRepository.findAll();

        List<Long> rolesValue = new ArrayList<>();

        for (Role role : roles){
            rolesValue.add(role.getId());
        }

        mockMvc.perform(post("/account/update?userid=" + userid)
                .param("arr[]", "1,2,3")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }



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
                .andExpect(status().isOk());
    }

    

}
