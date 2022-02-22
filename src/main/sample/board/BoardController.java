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
    @GetMapping("/pwPopup")
    public String PasswordPopup(Model model, @RequestParam(required = false) Long id) {

        boardService.get_pw_id(model, id);                                          // 게시글 ID 값을 입력 폼으로 던져주자.
        return "board/boardInsertPw";
    }

    @PostMapping("/pwPopup")
    public String PasswordPopupPost(PasswordForm pwForm, Model model) {        
        Optional<Board> PwSerchId = boardRepository.findById(pwForm.getId());       // 파라미터로 받은 id로 해당 엔티티갖고옴

        // db에서 id로 게시글 불러온 id 불러오기
        if (PwSerchId.isPresent()) {                                                // 정상접근이라면
            if (PwSerchId.get().getPassword().equals(pwForm.getPasswordFiled())) {  // 만약 입력 비밀번호가 같으면
                boardRepository.deleteById(pwForm.getId());                         // 삭제
            } else {
                return "redirect:/test/pwPopup?id=" + pwForm.getId();               // 비밀번호 틀렸을 시 그 창 그대로
            }
        }
        return "redirect:/test/list";                                               // 게시글 삭제 후 리스트 페이지로 리턴
    }

}