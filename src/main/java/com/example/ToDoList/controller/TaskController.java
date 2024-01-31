package com.example.ToDoList.controller;

import com.example.ToDoList.model.Tasks;
import com.example.ToDoList.model.ToDoList;
import com.example.ToDoList.model.User;
import com.example.ToDoList.service.TaskService;
import com.example.ToDoList.service.ToDoService;
import com.example.ToDoList.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class TaskController {
    private TaskService taskService;
    private ToDoService toDoService;
    private UserDetailsService userDetailsService;
    private UserService userService;

    @Autowired
    public TaskController(TaskService taskService, ToDoService toDoService, UserDetailsService userDetailsService,UserService userService) {
        this.taskService = taskService;
        this.toDoService = toDoService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }
    public void userData(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        userDetails.getUsername();
        model.addAttribute("userdetail", userDetails);
    }
    @GetMapping("/tasks")
    public String toDoList(Model model, Principal principal){
        userData(model,principal);
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("todolists", userService.getAllListsByUserName(principal.getName()));
        return "tasks";
    }
    @GetMapping("/list/new")
    public String createListForm(Model model, Principal principal){
        userData(model,principal);
        ToDoList toDoList = new ToDoList();
        toDoList.setColor("#8A2BE2");
        model.addAttribute("list", toDoList);
        return "create_list";
    }
    @GetMapping("/list/manage")
    public String menageLists(Model model, Principal principal){
        userData(model,principal);
        model.addAttribute("todolists", userService.getAllListsByUserName(principal.getName()));
        return "manage_lists";
    }
    @PostMapping("/list")
    public String saveList(@ModelAttribute("list") ToDoList toDoList,Model model, Principal principal){
        toDoService.saveList(toDoList);
        userData(model,principal);
        List<ToDoList> lists= userService.getAllListsByUserName(principal.getName());
        lists.add(toDoList);
        User user = userService.findByUserName(principal.getName());
        user.setToDoLists(lists);
        userService.updateUser(user);
        return "redirect:/tasks";
    }
    @GetMapping("/tasks/new")
    public String createTaskForm(Model model, Principal principal){
        userData(model,principal);
        Tasks task = new Tasks();
        model.addAttribute("task", task);
        return "create_task";
    }
    @PostMapping("/save/{id}")
    public String saveTask(@PathVariable Long id,@ModelAttribute("task") Tasks task) {
        Long lastID = taskService.getLastId()+1L;
        task.setId(lastID);
        taskService.saveTask(task);
        ToDoList existingList = toDoService.getListById(id);
        List<Tasks> allTasks = existingList.getTasks();
        allTasks.add(task);
        existingList.setTasks(allTasks);
        toDoService.updateList(existingList);
        return "redirect:/tasks";
    }
    @GetMapping("/tasks/new/{id}")
    public String editTaskForm(@PathVariable Long id, Model model, Principal principal){
        userData(model,principal);
        Tasks task = new Tasks();
        model.addAttribute("task", task);
        model.addAttribute("list", toDoService.getListById(id));
        return "create_task";
    }
    @GetMapping("/tasks/edit/{id}")
    public String addTaskForm(@PathVariable Long id, Model model, Principal principal){
        userData(model,principal);
        model.addAttribute("task", taskService.getTaskById(id));
        return "edit_task";
    }
    @GetMapping("/list/edit/{id}")
    public String editList(@PathVariable Long id, Model model, Principal principal){
        userData(model,principal);
        model.addAttribute("list", toDoService.getListById(id));
        return "edit_list";
    }
    @PostMapping("/list/{id}")
    public String updateList(@PathVariable Long id, @ModelAttribute("list") ToDoList toDoList){
        ToDoList existingList = toDoService.getListById(id);
        existingList.setToDoId(id);
        existingList.setName(toDoList.getName());
        existingList.setColor(toDoList.getColor());
        toDoService.updateList(existingList);
        return "redirect:/tasks";
    }
    @PostMapping("/tasks/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute("task") Tasks tasks){
        Tasks existingTask = taskService.getTaskById(id);
        existingTask.setId(id);
        existingTask.setName(tasks.getName());
        existingTask.setDescription(tasks.getDescription());
        existingTask.setDate(tasks.getDate());
        taskService.updateTask(existingTask);
        return "redirect:/tasks";
    }
    @GetMapping("/tasks/{id}")
    public String deleteTask(@PathVariable Long id){
       taskService.deleteTaskById(id);
       return "redirect:/tasks";
    }
    @GetMapping("/list/{id}")
    public String deleteList(@PathVariable Long id){
        toDoService.deleteListById(id);
        taskService.deleteListOfTasks(toDoService.tasksIdsWithoutList(taskService.getAllTasks()));
        return "redirect:/tasks";
    }
}
