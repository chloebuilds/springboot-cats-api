package com.example.chloeSpringApi.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "cats") // ! Name the table in our database
public class Cat {

    @Id // ! This is the unique ID for our model. Every entity needs one.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String breed;

    @Column(length=5000)
    private String description;

    @Column
    private String lifeSpan;

    @Column
    private String personality;
}
