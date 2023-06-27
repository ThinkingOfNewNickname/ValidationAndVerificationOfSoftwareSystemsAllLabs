package com.onlinegamestore.onlinegamestore.controller;

import com.onlinegamestore.onlinegamestore.models.User;
import com.onlinegamestore.onlinegamestore.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Test
    void registerUser() {
        UserController controller = new UserController();

        String viewName = controller.registerUser();

        assertEquals("register", viewName);
    }

    @Test
    void userLogin() {
        UserController controller = new UserController();
        String viewName = controller.userLogin(new ExtendedModelMap());
        assertEquals("userLogin", viewName);
    }

    @Test
    void userLoginValidate() {
        UserService userService = Mockito.mock(UserService.class);
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");

        Mockito.when(userService.validateUser("test", "test")).thenReturn(user);

        UserController controller = new UserController();
        controller.setUserService(userService);

        MockHttpServletResponse response = new MockHttpServletResponse();
        ModelAndView mv = controller.userLoginValidate("test", "test", new ExtendedModelMap(), response);

        assertEquals("index", mv.getViewName());
        assertEquals(user, mv.getModel().get("user"));
        assertEquals("No products are available", mv.getModel().get("msg"));

        Cookie cookie = response.getCookie("username");
        assertNotNull(cookie);
        assertEquals("test", cookie.getValue());
    }

    @Test
    void newUseRegister() {
        UserService userService = Mockito.mock(UserService.class);
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setEmail("test@test.com");

        Mockito.when(userService.hasUser("test")).thenReturn(false);

        UserController controller = new UserController();
        controller.setUserService(userService);

        ModelAndView mv = controller.newUseRegister(user);

        assertEquals("userLogin", mv.getViewName());

        // Username already exists
        Mockito.when(userService.hasUser("test")).thenReturn(true);
        mv = controller.newUseRegister(user);
        assertEquals("register", mv.getViewName());
        assertEquals("Username already exists", mv.getModel().get("message"));
    }
}