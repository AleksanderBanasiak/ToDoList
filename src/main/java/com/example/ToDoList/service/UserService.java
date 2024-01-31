package com.example.ToDoList.service;

import com.example.ToDoList.dto.UserDto;
import com.example.ToDoList.model.ToDoList;
import com.example.ToDoList.model.User;

import java.util.List;

public interface UserService {

    User findByUserName(String userName);
    boolean isEmailExist(String email);
    User save(UserDto userDto);

    List<ToDoList> getAllListsByUserName(String name);

    void updateUser(User user);

}
