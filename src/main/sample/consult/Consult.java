package com.sample.sample.consult;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CONSULT")
public class Consult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 질문
    @Column(nullable = false, length = 40)
    private String userid;

    @Column(nullable = false, length = 350)
    private String consultTitle;

    @Lob
    private String consultText;

    // 답변
    @Column(length = 40)
    private String answerid;

    @Column(length = 350)
    private String answerTitle;

    @Lob
    private String answerText;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;

    private LocalDateTime answerTime;

}