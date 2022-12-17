package com.example.randomnumberclientserverapp.dao;

import com.example.randomnumberclientserverapp.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    User user;
    @BeforeEach
    void setUp() {
        user = new User("Tom", "gho_123qwerty", 12345);
    }

    @Test
    void addUser() {
        User newUser = new User();
        UserDAO.addUser(newUser);
        assertEquals(2, UserDAO.users.size());
    }

    @Test
    void addExistingUser() {
        user = new User("Tom", "gho_asdfasdf", 12345);
        assertEquals("gho_asdfasdf", UserDAO.findUserByLogin("Tom").getGitHubToken());
    }

    @Test
    void findUserByLogin() {
        User actualUser = UserDAO.findUserByLogin("Tom");
        assertEquals(user, actualUser);

    }

    @Test
    void notFindUserByLogin() {
        assertThrows(RuntimeException.class, () -> UserDAO.findUserByLogin("Bob"));
    }

    @Test
    void userIsExist() {
        boolean actual = UserDAO.userIsExist("Tom");
        assertTrue(actual);
    }

    @Test
    void userIsNotExist() {
        boolean actual = UserDAO.userIsExist("Bob");
        assertFalse(actual);
    }

    @AfterEach
    void cleanUp() throws NoSuchFieldException, IllegalAccessException {
        UserDAO.users.clear();
        Field idCount = User.class.getDeclaredField("idCount");
        idCount.setAccessible(true);
        idCount.setInt(UserDAO.class, 0);
    }
}