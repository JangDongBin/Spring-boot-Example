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
        return newBoard;
    }

    public void check_pw(Model model, Long id) {
        Optional<Board> board = boardRepository.findById(id);

        if (board.isPresent()) {
            PasswordForm pwForm = PasswordForm.builder()
                    .id(board.get().getId())
                    .build();
            model.addAttribute("pwForm", pwForm);
        }
        
    }
}
