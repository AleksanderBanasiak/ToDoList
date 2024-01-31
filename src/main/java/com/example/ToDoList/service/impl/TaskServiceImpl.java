package com.example.ToDoList.service.impl;

import com.example.ToDoList.model.Tasks;
import com.example.ToDoList.repo.TaskRepo;
import com.example.ToDoList.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepo taskRepo;

    @Autowired
    public TaskServiceImpl(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }
    @Override
    public List<Tasks> getAllTasks() {
        return taskRepo.findAll();
    }



    @Override
    public Tasks saveTask(Tasks tasks) {
        return taskRepo.save(tasks);
    }
    @Override
    public Long getLastId() {
        List<Tasks>  allTasks= taskRepo.findAll();
        return allTasks.isEmpty() ? 0L : allTasks.get(allTasks.size()-1).getId();
    }
    @Override
    public Tasks updateTask(Tasks tasks) {
        return taskRepo.save(tasks);
    }
    @Override
    public Tasks getTaskById(Long id) {
        return taskRepo.findById(id).get();
    }
    @Override
    public void deleteTaskById(Long id) {
        taskRepo.deleteById(id);
    }
    @Override
    public void deleteListOfTasks(List<Long> ids) {
        ids.forEach(this::deleteTaskById);
    }
}
