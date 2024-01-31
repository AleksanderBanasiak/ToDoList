package com.example.ToDoList.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "todolist")
public class ToDoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long toDoId;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private String color;

    @OneToMany
    @JoinColumn(name = "toDoId")
    private List<Tasks> tasks;

    public ToDoList(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
