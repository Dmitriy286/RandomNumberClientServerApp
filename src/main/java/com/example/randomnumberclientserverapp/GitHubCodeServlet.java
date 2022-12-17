package com.example.incrcliservapp;

import com.example.incrcliservapp.service.GitHubApi;
import com.example.incrcliservapp.models.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(value = "/code")
public class GitHubCodeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");
        System.out.println("Code from GitHub: " + code);

        GitHubApi gitApi = new GitHubApi(code);
        User newUser = gitApi.createOrRestoreUser();

        String path = "/login.html";
        response.sendRedirect(path + "?token=" + newUser.getGitHubToken() + "&login=" + newUser.getLogin());
    }
}
