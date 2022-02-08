package com.sample.sample.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    

    @GetMapping("/detail-board")
    public String DetailBoard(){
        return "";
    }
}
