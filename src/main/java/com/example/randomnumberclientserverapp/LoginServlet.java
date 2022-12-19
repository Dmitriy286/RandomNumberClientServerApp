package com.example.randomnumberclientserverapp;

import com.example.randomnumberclientserverapp.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {
    private final static Logger log = LoggerFactory.getLogger(User.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("doPost login init");
        String login = request.getParameter("login");
        String tokenFromFront = request.getParameter("token");

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

        response.sendRedirect("/RandomNumberClientServerApp-1.0-SNAPSHOT/client.html");
    }
}
