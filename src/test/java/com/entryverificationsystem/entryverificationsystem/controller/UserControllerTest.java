package com.entryverificationsystem.entryverificationsystem.controller;

import com.entryverificationsystem.entryverificationsystem.models.User;
import com.entryverificationsystem.entryverificationsystem.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private final UserService userService = Mockito.mock(UserService.class);
    private final UserController userController = new UserController();

    @BeforeEach
    void setUp() {
        userController.setUserService(userService);
    }

    @Test
    void testRegisterUser() {
        String result = userController.registerUser();
        assertEquals("register", result);
    }

    @Test
    void testUserLoginWithCookie() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Model model = Mockito.mock(Model.class);
        String username = "testUser";

        when(request.getCookies()).thenReturn(new Cookie[]{new Cookie("username", username)});
        ModelAndView result = userController.userLogin(model, request);

        assertEquals("index", result.getViewName());
        assertEquals(username, result.getModel().get("username"));
    }

    @Test
    void testUserLoginWithoutCookie() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Model model = Mockito.mock(Model.class);

        when(request.getCookies()).thenReturn(new Cookie[]{});
        ModelAndView result = userController.userLogin(model, request);

        assertEquals("userLogin", result.getViewName());
    }

    @ParameterizedTest
    @CsvSource({
            "testUser, validPassword",
            "anotherUser, anotherPassword",
            "yetAnotherUser, yetAnotherPassword",
            "oneMoreUser, oneMorePassword",
            "lastUser, lastPassword",
            "finallyLastUser, finallyLastPassword",
            "trulyLastUser, trulyLastPassword",
            "reallyTrulyLastUser, reallyTrulyLastPassword",
            "noMoreUsers, noMorePasswords",
            "noMoreUsersAgain, noMorePasswordsAgain",
    })
    void testUserLoginValidateValidCredentials(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        when(userService.validateUser(username, password)).thenReturn(user);

        ModelAndView result = userController.userLoginValidate(username, password, Mockito.mock(Model.class), Mockito.mock(HttpServletResponse.class));

        assertEquals("index", result.getViewName());
        assertEquals(username, result.getModel().get("username"));
    }

    @Test
    void testUserLoginValidateInvalidCredentials() {
        String username = "testUser";
        String password = "invalidPassword";

        when(userService.validateUser(username, password)).thenReturn(new User());

        ModelAndView result = userController.userLoginValidate(username, password, Mockito.mock(Model.class), Mockito.mock(HttpServletResponse.class));

        assertEquals("userLogin", result.getViewName());
        assertEquals("Please enter correct username and password", result.getModel().get("message"));
    }

    @Test
    void testLogout() {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        String result = userController.logout(response);
        assertEquals("userLogin", result);
    }

    @ParameterizedTest
    @CsvSource({
            "newUser, password",
            "anotherNewUser, anotherPassword",
            "yetAnotherNewUser, yetAnotherPassword",
            "oneMoreNewUser, oneMorePassword",
            "lastNewUser, lastPassword",
            "finallyLastNewUser, finallyLastPassword",
            "trulyLastNewUser, trulyLastPassword",
            "reallyTrulyLastNewUser, reallyTrulyLastPassword",
            "noMoreNewUsers, noMoreNewPasswords",
            "noMoreNewUsersAgain, noMoreNewPasswordsAgain",
    })
    void testNewUserRegisterNewUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        when(userService.hasUser(username)).thenReturn(false);
        ModelAndView result = userController.newUseRegister(user);

        assertEquals("userLogin", result.getViewName());
    }

    @Test
    void testNewUserRegisterExistingUser() {
        User user = new User();
        user.setUsername("existingUser");
        user.setPassword("password");

        when(userService.hasUser(user.getUsername())).thenReturn(true);
        ModelAndView result = userController.newUseRegister(user);

        assertEquals("register", result.getViewName());
        assertEquals("Username already exists", result.getModel().get("message"));
    }
}