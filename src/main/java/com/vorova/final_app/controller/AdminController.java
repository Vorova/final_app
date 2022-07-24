package com.vorova.final_app.controller;

import com.vorova.final_app.model.User;
import com.vorova.final_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping()
    public String admin(Model model, Principal user) {
        model.addAttribute("title", "List users");
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("user", userService.getByUsername(user.getName()));
        model.addAttribute("newUser", new User());
        return "admin/index";
    }

    @PostMapping(value = "/add")
    public String add(@ModelAttribute User user, @RequestParam Long[] roles) {
        userService.save(user, roles);
        return "redirect:/admin";
    }

}
