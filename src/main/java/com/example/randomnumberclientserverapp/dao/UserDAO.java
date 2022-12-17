package com.example.incrcliservapp.dao;

import com.example.incrcliservapp.models.User;

import java.util.*;
import java.util.stream.Collectors;

public class UserDAO {
    public static final List<User> users = new ArrayList<>();

    public static void addUser(User user) {
        if (!users.stream().map(e -> e.getLogin()).collect(Collectors.toList()).contains(user.getLogin())) {
            users.add(user);
        } else {
            System.out.println("User with such login already exists");
            findUserByLogin(user.getLogin()).setGitHubToken(user.getGitHubToken());
        }
        System.out.println(users);
    }

    public static User findUserByLogin(String login) throws RuntimeException {
        System.out.println(login);
        User foundUser = users.stream().filter(e -> e.getLogin().equals(login)).findFirst()
                .orElseThrow(() -> new RuntimeException("Such user does not exist"));

        return foundUser;

    }

    public static boolean userIsExist(String login) {
        boolean result = users.stream().anyMatch(e -> e.getLogin().equals(login));
        return result;
    }
}
