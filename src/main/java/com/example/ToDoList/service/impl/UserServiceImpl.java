package com.example.ToDoList.service.impl;

import com.example.ToDoList.dto.UserDto;
import com.example.ToDoList.model.ToDoList;
import com.example.ToDoList.model.User;
import com.example.ToDoList.repo.UserRepo;
import com.example.ToDoList.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;
    private UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    @Override
    public User findByUserName(String userName) {
        return userRepo.findByName(userName);
    }
    @Override
    public boolean isEmailExist(String email) {
        boolean flag = false;
        List<User> users = userRepo.findAll();
        if(!users.isEmpty()){
            for (User user : users) {
                flag =user.getEmail().equals(email);
                if(flag){
                    break;
                }
            }
        }
        return flag;
    }
    @Override
    public User save(UserDto userDto) {
        User user = new User(userDto.getName(),userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()));
        return userRepo.save(user);
    }
    @Override
    public List<ToDoList> getAllListsByUserName(String name) {
        return userRepo.findByName(name).getToDoLists();
    }
    @Override
    public void updateUser(User user) {
        userRepo.save(user);
    }
}
