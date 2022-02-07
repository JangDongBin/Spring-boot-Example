package com.sample.sample.entity;

import javax.persistence.*;

import lombok.Data;

@Data //인식 안됨
@Entity
@Table(name = "TEST")
public class testEntity {

    @Id
    private Long id;

    @Column(length = 255, nullable = true)
    private String title;

    @Column(length = 255, nullable = true)
    private String name;

    @Column(length = 255, nullable = true)
    private String created;
}
