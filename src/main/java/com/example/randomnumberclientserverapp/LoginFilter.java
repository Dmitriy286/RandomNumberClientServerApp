package com.example.randomnumberclientserverapp;


import com.example.randomnumberclientserverapp.dao.UserDAO;
import com.example.randomnumberclientserverapp.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;


@WebFilter(value = "/login")
public class LoginFilter implements Filter {
    private final static Logger log = LoggerFactory.getLogger(User.class);

    private ServletContext context;

    public void init(FilterConfig filterConfig) {
        this.context = filterConfig.getServletContext();
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("Init doLoginFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String tokenFromFront = request.getParameter("token");
        String login = request.getParameter("login");

        User userToCompare = UserDAO.findUserByLogin(login);

        if (userToCompare.getGitHubToken().equals(tokenFromFront)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            PrintWriter out = servletResponse.getWriter();
            out.println("<font color=red>Username is incorrect or token is expired</font>");
            log.error("Username is incorrect or token is expired");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            RequestDispatcher requestDispatcher = request.getServletContext().getRequestDispatcher("/index.html");
            requestDispatcher.include(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
