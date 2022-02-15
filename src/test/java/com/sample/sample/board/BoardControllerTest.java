package com.sample.sample.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BoardControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private BoardRepository boardRepository;

    @DisplayName("게시글 입력 - 정상")
    @Test
    void board_insert_true() throws Exception {
        mockMvc.perform(post("/test/board-add")
                .param("useridField", "jangdongbin")
                .param("TitleField", "게시글 입력 정상")
                .param("TextField", "게시글 입력 정상입니다.")
                .param("PasswordField", "1111"))
                .andExpect(status().is3xxRedirection());
    }
    
    @DisplayName("게시글 입력 - 오류")
    @Test
    void board_insert_false() throws Exception {
        mockMvc.perform(post("/test/board-add")
                .param("useridField", "jangdongbin")
                .param("TitleField", "") // title 공백
                .param("TextField", "게시글 입력 정상입니다.")
                .param("PasswordField", "1111"))
                .andExpect(status().isOk());
    }

    @DisplayName("게시글 수정 - 정상")
    @Test
    void board_update_ture() throws Exception {
        mockMvc.perform(post("/test/board-add")
                    .param("id", "1")
                    .param("useridField", "jangdongbin")
                    .param("TitleField", "게시글 수정 정상")
                    .param("TextField", "게시글 수정 정상입니다.")
                    .param("PasswordField", "1111"))
                    .andExpect(status().isOk());
    }

    @DisplayName("게시글 수정 - 오류")
    @Test
    void board_update_false() throws Exception {
        mockMvc.perform(post("/test/board-add")
                    .param("id", "1")
                    .param("useridField", "jangdongbin")
                    .param("TitleField", "")//title 공백
                    .param("TextField", "게시글 수정 오류입니다.")
                    .param("PasswordField", "1111"))
                    .andExpect(status().isOk());
    }
}
