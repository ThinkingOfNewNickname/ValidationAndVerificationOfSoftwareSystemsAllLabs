package com.entryverificationsystem.entryverificationsystem.ordertests;

import com.entryverificationsystem.entryverificationsystem.dao.UserDao;
import com.entryverificationsystem.entryverificationsystem.models.User;
import com.entryverificationsystem.entryverificationsystem.services.UserService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceFlowTest {

    private static final String username = "username";
    private static final String password = "password";

    private static User savedUser;


    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Order(1)
    @Test
    public void addUser() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        when(userDao.saveUser(any(User.class))).thenAnswer(i -> {
            User savedUser = i.getArgument(0);
            savedUser.setId(1);
            return savedUser;
        });

        savedUser = userService.addUser(user);
        assertNotNull(savedUser);
        assertEquals(1, savedUser.getId());
    }

    @Order(2)
    @Test
    public void hasUser() {
        when(userDao.hasUser(username)).thenReturn(savedUser != null && savedUser.getId() == 1);

        assertTrue(userService.hasUser(username));
    }

    @Order(3)
    @Test
    public void validateUser() {
        when(userDao.validateUser(username, password)).thenReturn(savedUser);

        User validatedUser = userService.validateUser(username, password);
        assertEquals(savedUser, validatedUser);
        assertNotNull(savedUser);
    }
}