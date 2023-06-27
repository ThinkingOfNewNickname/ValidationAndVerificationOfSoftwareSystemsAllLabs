package com.entryverificationsystem.entryverificationsystem.dao;

import com.entryverificationsystem.entryverificationsystem.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.persistence.NoResultException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDaoTest {

    private SessionFactory sessionFactory;
    private Session session;
    private UserDao userDao;
    private Query query;

    @BeforeEach
    void setUp() {
        sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);
        userDao = new UserDao();
        query = mock(Query.class);

        userDao.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");

        User result = userDao.saveUser(user);

        assertEquals(user, result);
    }

    @ParameterizedTest
    @CsvSource({
            "existingUser",
            "anotherExistingUser",
            "yetAnotherExistingUser",
            "andAnotherExistingUser",
            "andYetAnotherExistingUser"
    })
    void testHasUserExists(String username) {
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(new User());

        boolean result = userDao.hasUser(username);

        assertTrue(result);
    }

    @Test
    void testHasUserNotExists() {
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenThrow(NoResultException.class);

        boolean result = userDao.hasUser("nonExistingUser");

        assertFalse(result);
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
    void testValidateUserValidCredentials(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        when(session.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(user);

        User result = userDao.validateUser(username, password);

        assertEquals(user, result);
    }

    @ParameterizedTest
    @CsvSource({
            "validUser, invalidPassword",
            "anotherValidUser, anotherInvalidPassword",
            "yetAnotherValidUser, yetAnotherInvalidPassword",
            "oneMoreValidUser, oneMoreInvalidPassword",
            "lastValidUser, lastInvalidPassword",
            "finallyLastValidUser, finallyLastInvalidPassword",
            "trulyLastValidUser, trulyLastInvalidPassword"
    })
    void testValidateUserInvalidPassword(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("correctPassword");

        when(session.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(user);

        User result = userDao.validateUser(username, password);

        assertNotEquals(user, result);
    }

    @Test
    void testValidateUserNotExists() {
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenThrow(NoResultException.class);

        User result = userDao.validateUser("nonExistingUser", "password");

        assertNotEquals("nonExistingUser", result.getUsername());
    }
}