package com.sample.sample.board;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/test")
public class BoardController {

    private final BoardService boardService;
    

    @GetMapping("/list")
    public String HomeList(){ // 용훈
        return "test";
    }

    //게시글 작성 및 수정
    @GetMapping("board-detail")
    public String detail(Model model, @RequestParam(required = false) Long id){

        boardService.detailProcess(model,id);
        model.addAttribute("title", "게시글 작성");
        return "board/boardaddForm";
    }

    @PostMapping("/addboard")
    public String Post_AddBoard(BoardForm boardForm, Model model){
        Board newbBoard = boardService.updateProcess(boardForm);
        
        return "redirect:/board-detail?id="+newbBoard.getId();
    }
    

    @GetMapping("/detail-board")
    public String DetailBoard(){
        return "";
    }
}
