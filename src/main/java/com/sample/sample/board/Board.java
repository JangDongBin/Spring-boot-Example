package com.sample.sample.board;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "BOARD")
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String Title;

    @Column(nullable = false, length = 400)
    private String Text;

    @Column(nullable = false, length = 10)
    private String Password;

    @CreationTimestamp
    private CreationTimestamp creationTimestamp;

    @UpdateTimestamp
    private UpdateTimestamp updateTimestamp;
}