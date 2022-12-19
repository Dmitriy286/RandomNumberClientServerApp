package com.example.randomnumberclientserverapp;

import com.example.randomnumberclientserverapp.service.GitHubApi;
import com.example.randomnumberclientserverapp.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(value = "/code")
public class GitHubCodeServlet extends HttpServlet {
    private final static Logger log = LoggerFactory.getLogger(User.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");
        log.info("Code from GitHub: {}", code);

        GitHubApi gitApi = new GitHubApi(code);
        User newUser = gitApi.createOrRestoreUser();

        String path = "/RandomNumberClientServerApp-1.0-SNAPSHOT/login.html";
        response.sendRedirect(path + "?token=" + newUser.getGitHubToken() + "&login=" + newUser.getLogin());
    }
}
