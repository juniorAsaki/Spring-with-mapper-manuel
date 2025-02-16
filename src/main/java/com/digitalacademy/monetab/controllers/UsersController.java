package com.digitalacademy.monetab.controllers;

import com.digitalacademy.monetab.models.Adress;
import com.digitalacademy.monetab.models.User;
import com.digitalacademy.monetab.services.AdressService;
import com.digitalacademy.monetab.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdressService adressService;


    @GetMapping
    public String showUserPage(Model model) {

        model.addAttribute("users", userService.findAll());
        return "users/list";
    }

    @GetMapping("/add")
    public String showAddUserPage(Model model){
        User user = new User();
        user.setAdress(new Adress());
        model.addAttribute("user", user);
        model.addAttribute("action", "add");
        return "users/forms";
    }

    @GetMapping("/update/{id}")
    public String showUpdateUserPage(@PathVariable Long id, Model model)
    {

        Optional<User> user = userService.findById(id);

        if(user.isPresent()){
            model.addAttribute("user", user);
            model.addAttribute("action", "update");
            return "users/forms";
        }else{
            return "redirect:/users";
        }

    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user){
        log.info("id save user {}", user.getId_user());

        adressService.save(user.getAdress());
        user.setCreatedDate(Instant.now());
        userService.save(user);
        return "redirect:/users";

    }
}
