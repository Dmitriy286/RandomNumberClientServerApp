package com.example.incrcliservapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("doPost login init");
        String login = request.getParameter("login");
        String tokenFromFront = request.getParameter("token");
        System.out.println(tokenFromFront);
        System.out.println(login);

        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(15 * 60);
        session.setAttribute("token", tokenFromFront);
        session.setAttribute("login", login);

        Cookie cookieToken = new Cookie("token", tokenFromFront);
        cookieToken.setMaxAge(15 * 60);
        Cookie cookieLogin = new Cookie("login", login);
        cookieToken.setMaxAge(15 * 60);
        response.addCookie(cookieToken);
        response.addCookie(cookieLogin);

        response.sendRedirect("/client.html");
    }
}
