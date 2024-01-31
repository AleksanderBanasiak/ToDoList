package com.example.ToDoList.controller;

import com.example.ToDoList.dto.UserDto;
import com.example.ToDoList.model.ToDoList;
import com.example.ToDoList.model.User;
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
public class UserController {



    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String login(Model model, UserDto userDto) {
        model.addAttribute("user", userDto);
        return "login";
    }
    @GetMapping("/register")
    public String register(Model model, UserDto userDto){
        model.addAttribute("user", userDto);
        return "register";
    }
    @PostMapping("/register")
    public String registerSave(@ModelAttribute("user") UserDto userDto, Model model){
        User user = userService.findByUserName(userDto.getName());
        if(user != null ){
            model.addAttribute("userexists", user);
            return "register";
        }
        if (userService.isEmailExist(userDto.getEmail())){
            model.addAttribute("emailexists", userDto);
            return "register";
        }
        userService.save(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("test")
    @ResponseBody
    public List<ToDoList> test(){
        return userService.getAllListsByUserName("xd");
    }


}
