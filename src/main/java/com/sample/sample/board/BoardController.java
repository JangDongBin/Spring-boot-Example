package com.sample.sample.board;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    private final BoardRepository boardRepository;
    private final BoardService boardService;
    private final BoardFormValidator boardFormValidator;

    @InitBinder("boardForm")
    public void InitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(boardFormValidator);
    }

    // 용훈
    @GetMapping("/list")
    public String HomeList(Model model, @PageableDefault(size = 20) Pageable pageable,
            @RequestParam(required = false, defaultValue = "") String searchString) {
        Page<Board> boardPagingList = boardRepository
                .findByUseridContainingOrTitleContainingOrderByIdDesc(searchString, searchString, pageable);

        int startPage = Math.max(1,
                (boardPagingList.getPageable().getPageNumber() / pageable.getPageSize()) * pageable.getPageSize()
                        + 1);
        int endPage = Math.min(boardPagingList.getTotalPages(), startPage + pageable.getPageSize() - 1);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("list", boardPagingList);

        model.addAttribute("searchString", searchString);
        model.addAttribute("title", "리스트");

        return "board/boardList";
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
    public String DetailBoard(Model model, @RequestParam Long id) {
        boardService.detailProcess(model, id);
        return "board/boardDetail";
    }

    // 게시글 삭제 //준호
    @GetMapping("/delete")
    public String Delete_board(Model model, @RequestParam Long id) {
        return "";
    }

    @GetMapping("/pwPopup")
    public String PasswordPopup(Model model, @RequestParam(required = false) Long id) {

        boardService.check_pw(model, id);
        return "board/boardInsertPw";
    }

    @PostMapping("/pwPopup")
    public String PasswordPopupPost(PasswordForm pwForm, Model model) {
        System.out.println("\n\n\n\n\n답변 폼 :: " + pwForm + "\n\n\n\n\n\n");
       
        /*  Optional<Board> 



        if (id != null) {
            Optional<Board> board = boardRepository.findById(id);

            if (board.isPresent()) {
                BoardForm boardForm = BoardForm.builder()
                        .useridField(board.get().getUserid())
                        .TitleField(board.get().getTitle())
                        .TextField(board.get().getText())
                        .PasswordField(board.get().getPassword()).build();

                String Inputpassword = boardForm.getPasswordField();

            }
            

        }  */

        return "redirect:/test/list";
    } 
}