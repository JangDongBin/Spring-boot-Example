package com.sample.sample.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>{
    Page<Board> findByUseridContainingOrTitleContainingOrderByIdDesc(String searchString, String searchString2,
            Pageable pageable);
}
