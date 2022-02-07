package com.sample.sample.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 100)
    private String title;

    @Column(length = 100)
    private String memo;

    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created = LocalDateTime.now();
    
    @UpdateTimestamp
    @Column(name ="updated")
    private LocalDateTime updated = LocalDateTime.now();
}
