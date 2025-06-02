package com.data.controller;

import com.data.dto.UserDTO;
import com.data.model.User;
import com.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
    @GetMapping("dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("register")
    public String register(Model model) {
        UserDTO user = new UserDTO();
        user.setRole("USER");
        user.setStatus(true);
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("register")
    public String registerAdd(@Valid @ModelAttribute("user") UserDTO user, BindingResult bindingResult, Model model) {
        List<User> users = userService.findAll();
        if (users.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))) {
            bindingResult.rejectValue("username", null, "Tên đã tồn tại!");
        }
        if (users.stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
            bindingResult.rejectValue("email", null, "Email đã tồn tại!");
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        newUser.setRole(user.getRole());
        newUser.setStatus(user.isStatus());
        userService.save(newUser);
        return "login";
    }

    @PostMapping("login")
    public String loginProcess(@ModelAttribute("user") User user, Model model, HttpSession session) {
        User existingUser = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (existingUser == null) {
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
            return "login";
        }

        if (!existingUser.isStatus()) {
            model.addAttribute("error", "Tài khoản đang bị khóa!");
            return "login";
        }

        session.setAttribute("userLogin", existingUser);

        if (existingUser.getRole().equals("ADMIN")) {
            return "redirect:/admin/bus";
        }
        return "redirect:/home";
    }
}