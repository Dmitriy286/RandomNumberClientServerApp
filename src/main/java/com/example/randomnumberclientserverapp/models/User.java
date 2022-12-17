package com.example.incrcliservapp.models;

import com.example.incrcliservapp.server.ServerEndPoint;
import com.example.incrcliservapp.dao.UserDAO;

public class User {
    static int idCount;
    int userId;
    int gitHubId;
    String login;
    String gitHubToken;
    ServerEndPoint clientConnection;

    public User() {

    }

    public User(String login, String gitHubToken, int gitHubId) {
        this.userId = ++idCount;
        this.login = login;
        this.gitHubToken = gitHubToken;
        this.gitHubId = gitHubId;
        UserDAO.addUser(this);
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

    public ServerEndPoint getClientConnection() {
        return clientConnection;
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
