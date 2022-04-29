package com.sample.sample.board;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void detailProcess(Model model, Long id) {
        if (id != null) {
            Optional<Board> board = boardRepository.findById(id);

            if (board.isPresent()) {
                BoardForm boardForm = BoardForm.builder()
                        .id(board.get().getId())
                        .useridField(board.get().getUserid())
                        .TitleField(board.get().getTitle())
                        .TextField(board.get().getText())
                        .PasswordField(board.get().getPassword())
                        .build();
                        
                model.addAttribute("boardForm", boardForm);
            }
        } else {
            model.addAttribute("boardForm", new BoardForm());
        }
    }

    public Board updateProcess(BoardForm boardForm) {
        Board newBoard;

        if (boardForm.getId() == null) {
            newBoard = Board.builder()
                    .id(null)
                    .userid(boardForm.getUseridField())
                    .title(boardForm.getTitleField())
                    .Text(boardForm.getTextField())
                    .Password(boardForm.getPasswordField())
                    .build();
        } else {
            newBoard = Board.builder()
                    .id(boardForm.getId())
                    .userid(boardForm.getUseridField())
                    .title(boardForm.getTitleField())
                    .Text(boardForm.getTextField())
                    .Password(boardForm.getPasswordField())
                    .build();
        }

        boardRepository.save(newBoard);
        //System.out.println(newBoard);
        return newBoard;
    }

    public void get_pw_id(Model model, Long id) { //파라미터로 받아온 id
        Optional<Board> board = boardRepository.findById(id);
        // db에서 id로 게시글 불러오기
        if (board.isPresent()) {
            PasswordForm pwForm = PasswordForm.builder() //객체생성
                    .id(board.get().getId()) // db에 있는 게시글 id
                    .build();
            model.addAttribute("pwForm", pwForm);
            //System.out.println("\n\n\n\n\ncheck pw 실행\n\n\n\n\n");
        }
    }
}
