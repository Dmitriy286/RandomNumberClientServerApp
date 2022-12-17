package com.example.incrcliservapp;


import com.example.incrcliservapp.dao.UserDAO;
import com.example.incrcliservapp.models.User;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;


@WebFilter(value = "/login")
public class LoginFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig filterConfig) {
        this.context = filterConfig.getServletContext();
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Init doLoginFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String tokenFromFront = request.getParameter("token");
        String login = request.getParameter("login");
        System.out.println(tokenFromFront);
        System.out.println(login);

        String uri = request.getRequestURI();
        System.out.println("Requested Resource::" + uri);

        User userToCompare = UserDAO.findUserByLogin(login);
        System.out.println("userToCompare.getGitHubToken():");
        System.out.println(userToCompare.getGitHubToken());
        if (userToCompare.getGitHubToken().equals(tokenFromFront)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            PrintWriter out = servletResponse.getWriter();
            out.println("<font color=red>Username is incorrect or token is expired</font>");
            System.out.println("<font color=red>Username is incorrect or token is expired</font>");
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
