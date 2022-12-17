package com.example.incrcliservapp;

import com.example.incrcliservapp.dao.UserDAO;
import com.example.incrcliservapp.models.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(value = "/auth")
public class AuthFilter implements Filter {
    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) {
        this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Init doAuthFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        System.out.println("Requested Resource::" + uri);

        String tokenFromSession = "null token";
        String loginFromSession = "";

        HttpSession session = request.getSession(false);


        if (session != null) {
            System.out.println("session id:");
            System.out.println(session.getId());
            loginFromSession = (String) session.getAttribute("login");
            tokenFromSession = (String) session.getAttribute("token");
            System.out.println("tokenFromSession:");
            System.out.println(tokenFromSession);
            System.out.println("loginFromSession");
            System.out.println(loginFromSession);
            User userToCompare = UserDAO.userIsExist(loginFromSession) ?
                    UserDAO.findUserByLogin(loginFromSession) : new User("", "no token", 0);
            System.out.println("userToCompare:");
            System.out.println(userToCompare);
            if (tokenFromSession.equals(userToCompare.getGitHubToken())) {
                System.out.println("Access granted");
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                System.out.println("Unauthorized access request (inner)");
                response.sendRedirect("index.html");
            }
        } else {
            System.out.println("Unauthorized access request (outer)");
            response.sendRedirect("index.html");
        }
    }

    @Override
    public void destroy() {

    }
}
