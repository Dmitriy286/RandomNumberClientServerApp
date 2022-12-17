package com.example.randomnumberclientserverapp.dao;

import com.example.randomnumberclientserverapp.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class UserDAO {
    private final static Logger log = LoggerFactory.getLogger(User.class);

    public static final List<User> users = new ArrayList<>();

    /**
     * Adds user to the list of users stored in class UserDAO
     * While using the User constructor with three arguments adds user automatically.
     * While using empty User constructor it is necessary to add user to the list after creating.
     * @param user instance of user
     */
    public static void addUser(User user) {
        if (!users.stream().map(e -> e.getLogin()).collect(Collectors.toList()).contains(user.getLogin())) {
            users.add(user);
        } else {
            log.warn("User with such login already exists");
            findUserByLogin(user.getLogin()).setGitHubToken(user.getGitHubToken());
        }
        log.info(users.toString());
    }

    /**
     *
     * @param login String login of searched user
     * @return found user from lists of users in class UserDAO
     * @throws RuntimeException if user with passed login does not exist
     */
    public static User findUserByLogin(String login) throws RuntimeException {
        log.info("Found user: {}", login);
        User foundUser = users.stream().filter(e -> e.getLogin().equals(login)).findFirst()
                .orElseThrow(() -> {
                    log.error("Such user does not exist");
                    return new RuntimeException("Such user does not exist");
                });

        return foundUser;
    }

    /**
     * Checks if user with passed login exists
     * @param login String login of user
     * @return true or false depending on the result of checking
     */
    public static boolean userIsExist(String login) {
        boolean result = users.stream().anyMatch(e -> e.getLogin().equals(login));
        return result;
    }
}
