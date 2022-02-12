package com.sample.sample.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class BoardController {
    

    @GetMapping("/list")
    public String HomeList(){
        return "";
    }

    @GetMapping("/addboard")
    public String AddBoard(){
        return "";
    }

    //테스트
    //테스트
    

    @GetMapping("/detail-board")
    public String DetailBoard(){
        return "board/boardDetail";
    }

    @GetMapping("/pwPopup")
    public String PasswordPopup(){
        return "board/boardInsertPw";
    }

    @PostMapping("/pwPopup")
    public String PasswordPopupPost(){
        return "board/boardInsertPw";
    }

}
