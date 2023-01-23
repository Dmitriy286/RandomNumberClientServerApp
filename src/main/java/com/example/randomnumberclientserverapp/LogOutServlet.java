package com.example.randomnumberclientserverapp;

import com.example.randomnumberclientserverapp.dao.UserDAO;
import com.example.randomnumberclientserverapp.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogOutServlet extends HttpServlet {
    private final static Logger log = LoggerFactory.getLogger(User.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final HttpSession session = request.getSession(false);
        if (session != null) {
            String login = (String) session.getAttribute("login");

            User logoutUser = UserDAO.findUserByLogin(login);
            logoutUser.removeClientConnection();
            logoutUser.setGitHubToken("");
            session.removeAttribute("login");
            session.removeAttribute("token");
            session.invalidate();
            log.info("Session has been invalidated");
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setValue("");
            response.addCookie(cookie);
        }
        log.info("Cookies have been deleted");
        log.info("Redirecting to the main page...");

        response.sendRedirect("/index.html");
    }
}
