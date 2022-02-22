package com.sample.sample.consult;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultRepository extends JpaRepository<Consult, Long> {

    Page<Consult> findByUseridContainingOrConsultTitleContainingOrderByIdDesc(String searchString, String searchString2,
            Pageable pageable);
}