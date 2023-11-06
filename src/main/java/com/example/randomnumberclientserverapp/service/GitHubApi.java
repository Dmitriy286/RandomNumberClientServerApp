package com.example.randomnumberclientserverapp.service;

import com.example.randomnumberclientserverapp.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitHubApi {
    private final static Logger log = LoggerFactory.getLogger(User.class);

    String codeForToken;
    String clientId;
    String clientSecret;


    public GitHubApi(String codeForToken) {
        this.codeForToken = codeForToken;
//        this.clientId = "8d45b84171f5ae17cfce";
        this.clientId = "22473e191d74e75f8a71";
//        this.clientSecret = "6da33d3cd857e5692afc21e779c39acc10822b5c";
        this.clientSecret = "f4161e276f0cb6d18713d1c5c9d9577a6cbbaab5";
    }

    public String getToken() throws IOException {
        String data = returnDataFromGitHub();
        log.info("Data from GitHub: {}", data);

        Pattern pattern = Pattern.compile("access_token=(.+)&scope");
        Matcher matcher = pattern.matcher(data);
        matcher.find();
        String token = matcher.group(1);
        log.info("Got token: {}", token);
        return token;
    }

    public String returnDataFromGitHub() throws IOException {
        String url = "https://github.com/login/oauth/access_token" + "?" +
                "client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&code=" + codeForToken;

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("POST");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String data = in.readLine();

        log.info("Data from GitHub: {}", data);
        in.close();
        return data;
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

        Pattern pattern = Pattern.compile("login\":\"(.+)\",\"id\":(\\d+),\"node");
        Matcher matcher = pattern.matcher(data);

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

        return newUser;
    }


}
