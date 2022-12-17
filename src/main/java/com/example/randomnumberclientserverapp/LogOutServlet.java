package com.example.incrcliservapp;

import com.example.incrcliservapp.dao.UserDAO;
import com.example.incrcliservapp.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogOutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final HttpSession session = request.getSession(false);
        if (session != null) {
            String login = (String) session.getAttribute("login");

            User logoutUser = UserDAO.findUserByLogin(login);
            logoutUser.removeClientConnection();
            logoutUser.setGitHubToken("");
            session.removeAttribute("login");
            session.removeAttribute("token");
            session.invalidate();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            System.out.println("::Cookie::{" + cookie.getName() + "," + cookie.getValue() + "}");
            cookie.setValue("");
            response.addCookie(cookie);
        }
        response.sendRedirect("index.html");
    }
}
