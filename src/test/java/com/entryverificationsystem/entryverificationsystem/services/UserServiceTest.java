package com.entryverificationsystem.entryverificationsystem.services;

import com.entryverificationsystem.entryverificationsystem.dao.UserDao;
import com.entryverificationsystem.entryverificationsystem.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "existingUser",
            "anotherExistingUser",
            "yetAnotherExistingUser",
            "andAnotherExistingUser",
            "andYetAnotherExistingUser"
    })
    void hasUser(String username) {
        when(userDao.hasUser(username)).thenReturn(true);
        assertTrue(userService.hasUser(username));
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
            "noMoreUsersAgain, noMorePasswordsAgain"
    })
    void validateUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        when(userDao.validateUser(username, password)).thenReturn(user);
        assertEquals(user, userService.validateUser(username, password));
    }

    @Test
    void addUser() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");

        when(userDao.saveUser(user)).thenReturn(user);
        assertEquals(user, userService.addUser(user));
    }
}
