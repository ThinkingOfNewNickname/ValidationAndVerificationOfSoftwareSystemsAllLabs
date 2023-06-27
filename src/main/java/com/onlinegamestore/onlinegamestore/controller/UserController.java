package com.onlinegamestore.onlinegamestore.controller;

import com.onlinegamestore.onlinegamestore.models.User;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.onlinegamestore.onlinegamestore.services.UserService;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private GameService gameService;

    @GetMapping("/register")
    public String registerUser() {
        return "register";
    }

//    @GetMapping("/buy")
//    public String buy() {
//        return "buy";
//    }


    @GetMapping("/")
    public String userLogin(Model model) {

        return "userLogin";
    }

    @PostMapping(value = "userLoginValidate")
    public ModelAndView userLoginValidate(@RequestParam("username") String username, @RequestParam("password") String pass, Model model, HttpServletResponse res) {
        User u = this.userService.validateUser(username, pass);
        if (u.getUsername() != null && u.getUsername().equals(username) && u.getPassword().equals(pass)) {

            res.addCookie(new Cookie("username", u.getUsername()));
            ModelAndView mView = new ModelAndView("index");
            mView.addObject("user", u);
//            List<Game> games = this.gameService.getGames();

//            if (games.isEmpty()) {
            mView.addObject("msg", "No products are available");
//            } else {
//                mView.addObject("products", games);
//            }
            return mView;

        } else {
            ModelAndView mView = new ModelAndView("userLogin");
            mView.addObject("message", "Please enter correct username and password");
            return mView;
        }
    }


//    @GetMapping("/user/games")
//    public ModelAndView getGames() {
//
//        ModelAndView mView = new ModelAndView("games");
//
//        List<Game> games = this.gameService.getGames();
//
//        if (games.isEmpty()) {
//            mView.addObject("msg", "No products are available");
//        } else {
//            mView.addObject("products", games);
//        }
//
//        return mView;
//    }

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
