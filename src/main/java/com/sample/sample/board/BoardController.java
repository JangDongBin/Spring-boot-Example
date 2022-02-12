package com.sample.sample.board;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.Errors;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/test")
public class BoardController {

    private final BoardService boardService;
    private final BoardFormValidator boardFormValidator;

    @InitBinder("boardForm")
    public void InitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(boardFormValidator);
    }

    // 용훈
    @GetMapping("/list")
    public String HomeList() {
        return "test";
    }

    // 게시글 작성 및 수정 //동빈
    @GetMapping("/board-add")
    public String Add_board(Model model, @RequestParam(required = false) Long id) {
        boardService.detailProcess(model, id);
        return "board/boardAdd";
    }

    @PostMapping("/board-add")
    public String Post_AddBoard(@Valid BoardForm boardForm, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "board/boardAdd";
        }

        Board newbBoard = boardService.updateProcess(boardForm);
        return "redirect:/test/detail-board?id=" + newbBoard.getId();
    }

    @GetMapping("/detail-board")
    public String DetailBoard(Model model, @RequestParam(required = false) Long id) {
        boardService.detailProcess(model, id);
        return "board/boardDetail";
    }

    @GetMapping("/delete")
    public String Delete_board(Model model, @RequestParam Long id) {
        return "";
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
