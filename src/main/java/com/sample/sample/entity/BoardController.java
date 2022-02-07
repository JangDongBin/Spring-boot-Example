package com.sample.sample.entity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/entity")
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepostitory BoardRepostitory;

    @GetMapping("/board")
    public String board(Model model) {
        //select * from board;
        model.addAttribute("list",BoardRepostitory.findAll());
        return "entity/board";
    }

}