package com.example.incrcliservapp.service;

import com.example.incrcliservapp.dao.UserDAO;
import com.example.incrcliservapp.models.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitHubApi {
    String codeForToken;

    public GitHubApi(String codeForToken) {
        this.codeForToken = codeForToken;
    }

    public String getToken() throws IOException {
        String url = "https://github.com/login/oauth/access_token?client_id=8d45b84171f5ae17cfce" +
                "&client_secret=6da33d3cd857e5692afc21e779c39acc10822b5c" +
                "&code=" + codeForToken;

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("POST");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String data = in.readLine();
        System.out.println("36 string: " + data);

        System.out.println(data);
        Pattern pattern = Pattern.compile("access_token=(.+)&scope");
        Matcher matcher = pattern.matcher(data);
        System.out.println(matcher);
        matcher.find();
        String token = matcher.group(1);
        System.out.println(token);
        in.close();
        return token;
    }

    public String[] getUserData(String token) throws IOException {
        String[] dataArray = new String[2];
        String url = "https://api.github.com/user";

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + token);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String data = in.readLine();
        in.close();

        System.out.println(data);

        Pattern pattern = Pattern.compile("login\":\"(.+)\",\"id\":(\\d+),\"node");
        Matcher matcher = pattern.matcher(data);
        System.out.println(matcher);
        matcher.find();
        String login = matcher.group(1);
        String gitHubId = matcher.group(2);
        dataArray[0] = login;
        dataArray[1] = gitHubId;

        return dataArray;
    }

    public User createOrRestoreUser() throws IOException {
        String token = getToken();
        String[] dataArray = getUserData(token);
        String login = dataArray[0];
        int gitHubId = Integer.parseInt(dataArray[1]);
        User newUser = new User(login, token, gitHubId);
        System.out.println(UserDAO.users);
        System.out.println(newUser);
        System.out.println();

        return newUser;
    }


}
