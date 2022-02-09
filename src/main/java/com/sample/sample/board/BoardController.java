package com.sample.sample.board;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/test")
public class BoardController {
    

    @GetMapping("/list")
    public String HomeList(){ // 용훈
        return "test";
    }

    //게시글 작성 및 수정
    @GetMapping("/addboard")
    public String AddBoard(@RequestParam (required = false) Long id, Model model){
        model.addAttribute("title", "게시글 작성");
        return "/board/boardaddForm";
    }

    

    @GetMapping("/detail-board")
    public String DetailBoard(){
        return "";
    }
}
