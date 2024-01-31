package com.example.ToDoList.service;

import com.example.ToDoList.model.Tasks;
import com.example.ToDoList.model.ToDoList;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToDoService {
    List<ToDoList> getAllLists();
    ToDoList saveList(ToDoList toDoList);
    ToDoList getListById(Long id);
    ToDoList updateList(ToDoList toDoList);
    void deleteListById(Long id);
    List<Long> tasksIdsWithoutList(List<Tasks> tasks);


}
