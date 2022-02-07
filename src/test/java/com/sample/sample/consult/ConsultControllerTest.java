package com.sample.sample.consult;

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
public class ConsultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ConsultRepository consultRepository;

    @DisplayName("리팩토링 - 상담글 입력 - 정상")
    @Test
    void consultInsert_correct_RE() throws Exception {
        mockMvc.perform(post("/consult/consult-detail")
                .param("userid", "mismismis")
                .param("consultTitle", "리팩토링 제목입니다.")
                .param("consultText", "리팩토링은 선택이 아닌 필수입니다!"))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("리팩토링 - 상담글 입력 - 오류")
    @Test
    void consultInsert_wrong_RE() throws Exception {

        mockMvc.perform(post("/consult/consult-detail")
                .param("userid", "sdssdssds")
                .param("consultTitle", "") // title 공백
                .param("consultText", "testText"))
                .andExpect(status().isOk());
    }

    @DisplayName("리팩토링 - 상담글 수정 - 정상")
    @Test
    void consultUpdate_corrent_RE() throws Exception {
        mockMvc.perform(post("/consult/consult-detail")
                .param("id", "1")
                .param("userid", "kangminseung")
                .param("consultTitle", "리팩토링 수정된 제목입니다.")
                .param("consultText", "리팩토링 과정이 어떻게 되나요?"))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("리팩토링 - 상담글 수정 - 오류")
    @Test
    void consultUpdate_wrong_RE() throws Exception {

        mockMvc.perform(post("/consult/consult-detail")
                .param("id", "1")
                .param("userid", "kangminseung")
                .param("consultTitle", "")
                .param("consultText", ""))
                .andExpect(status().isOk());
    }

    @DisplayName("리팩토링 - 댓글 - 정상")
    @Test
    void consultAnswer_corrent_RE() throws Exception{
        mockMvc.perform(post("/consult/consult-answer")
                .param("id", "1")
                .param("answerTitle", "리팩토링 댓글 제목입니다.")
                .param("answerText", "리팩토링 댓글 내용 ㅋㅋㄹ삥뽕빵뿡"))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("리팩토링 - 댓글 - 오류")
    @Test
    void consultAnswer_wrong_RE() throws Exception{
        mockMvc.perform(post("/consult/consult-answer")
                .param("id", "1")
                .param("answerTitle", "")
                .param("answerText", "리팩토링 댓글 내용 ㅋㅋㄹ삥뽕빵뿡"))
                .andExpect(status().isOk());
    }
}