package com.entryverificationsystem.entryverificationsystem.controller;

import com.entryverificationsystem.entryverificationsystem.models.User;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.entryverificationsystem.entryverificationsystem.services.UserService;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerUser() {
        return "register";
    }

    @GetMapping("/")
    public ModelAndView userLogin(Model model, HttpServletRequest res) {
        for (Cookie cookie : res.getCookies()) {
            String name = cookie.getName();
            if (name.equals("username") && !cookie.getValue().equals("")) {
                ModelAndView mView = new ModelAndView("index");
                mView.addObject("username", cookie.getValue());

                return mView;
            }
        }

        return new ModelAndView("userLogin");
    }



    @PostMapping(value = "userLoginValidate")
    public ModelAndView userLoginValidate(@RequestParam("username") String username,
                                          @RequestParam("password") String pass,
                                          Model model,
                                          HttpServletResponse res) {
        User u = this.userService.validateUser(username, pass);
        if (u.getUsername() != null && u.getUsername().equals(username) && u.getPassword().equals(pass)) {

            res.addCookie(new Cookie("username", u.getUsername()));
            ModelAndView mView = new ModelAndView("index");
            mView.addObject("username", u.getUsername());

            return mView;

        } else {
            ModelAndView mView = new ModelAndView("userLogin");
            mView.addObject("message", "Please enter correct username and password");
            return mView;
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse res) {
        res.addCookie(new Cookie("username", ""));
        return "userLogin";
    }


    @PostMapping(value = "newUserRegister")
    public ModelAndView newUseRegister(User user) {
        if (this.userService.hasUser(user.getUsername())) {
            ModelAndView mView = new ModelAndView("register");
            mView.addObject("message", "Username already exists");
            return mView;
        }

        user.setRole("ROLE_NORMAL");
        this.userService.addUser(user);

        return new ModelAndView("userLogin");
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
