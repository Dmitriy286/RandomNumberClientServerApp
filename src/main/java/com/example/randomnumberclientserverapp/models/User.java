package com.example.randomnumberclientserverapp.models;

import com.example.randomnumberclientserverapp.server.ServerEndPoint;
import com.example.randomnumberclientserverapp.dao.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class User {
    private final Logger log = LoggerFactory.getLogger(User.class);

    static int idCount;
    int userId;
    int gitHubId;
    String login;
    String gitHubToken;
    ServerEndPoint clientConnection;

    public User() {
        this.userId = ++idCount;
        log.info("User with empty fields created");
    }

    public User(String login, String gitHubToken, int gitHubId) {
        this.userId = ++idCount;
        this.login = login;
        this.gitHubToken = gitHubToken;
        this.gitHubId = gitHubId;
        log.info("User created");
        UserDAO.addUser(this);
        log.info("User added to the repository");
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getGitHubToken() {
        return gitHubToken;
    }

    public void setGitHubToken(String gitHubToken) {
        this.gitHubToken = gitHubToken;
    }

    public void setClientConnection(ServerEndPoint clientConnection) {
        this.clientConnection = clientConnection;
    }

    public void removeClientConnection() {
        this.clientConnection = null;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", gitHubToken='" + gitHubToken + '\'' +
                ", gitHubId='" + gitHubId + '\'' +
                ", clientConnection=" + clientConnection +
                '}';
    }
}
