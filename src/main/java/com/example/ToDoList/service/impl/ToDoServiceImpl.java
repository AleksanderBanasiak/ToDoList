package com.example.ToDoList.service.impl;

import com.example.ToDoList.model.Tasks;
import com.example.ToDoList.model.ToDoList;
import com.example.ToDoList.repo.ToDoRepo;
import com.example.ToDoList.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ToDoServiceImpl implements ToDoService {

    private ToDoRepo toDoRepo;

    @Autowired
    public ToDoServiceImpl(ToDoRepo toDoRepo) {
        this.toDoRepo = toDoRepo;
    }
    @Override
    public List<ToDoList> getAllLists() {
        return toDoRepo.findAll();
    }
    @Override
    public ToDoList saveList(ToDoList toDoList) {
        return toDoRepo.save(toDoList);
    }
    @Override
    public ToDoList getListById(Long id) {
        return toDoRepo.findById(id).get();
    }

    @Override
    public ToDoList updateList(ToDoList toDoList) {
        return toDoRepo.save(toDoList);
    }
    @Override
    public void deleteListById(Long id) {
        toDoRepo.deleteById(id);
    }
    @Override
    public List<Long> tasksIdsWithoutList(List<Tasks> tasks) {
        List<ToDoList> toDoLists = getAllLists();
        List<Long> ids = new ArrayList<>();
        List<Tasks> allTasks = new ArrayList<>();
        for (ToDoList toDoList : toDoLists) {
            List<Tasks> task = toDoList.getTasks();
            allTasks.addAll(task);
        }
        List<String> allStringNames = new ArrayList<>();
        for (Tasks allTask : allTasks) {
            allStringNames.add(allTask.getName());
        }
        for (Tasks task : tasks) {
            if (!allStringNames.contains(task.getName())) {
                ids.add(task.getId());
            }
        }
        return ids;
    }
}
