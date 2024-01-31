package com.example.ToDoList.service;

import com.example.ToDoList.model.Tasks;

import java.util.List;

public interface TaskService {
    List<Tasks> getAllTasks();
    Tasks saveTask(Tasks tasks);
    Long getLastId();
    Tasks updateTask(Tasks tasks);
    Tasks getTaskById(Long id);
    void deleteTaskById(Long id);
    void deleteListOfTasks(List<Long> ids);
}
