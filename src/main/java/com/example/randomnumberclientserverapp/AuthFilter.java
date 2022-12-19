package com.example.randomnumberclientserverapp;

import com.example.randomnumberclientserverapp.dao.UserDAO;
import com.example.randomnumberclientserverapp.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(value = "/auth")
public class AuthFilter implements Filter {
    private final static Logger log = LoggerFactory.getLogger(User.class);

    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) {
        this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("Init doAuthFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        log.info("Requested Resource::" + uri);

        String tokenFromSession = "null token";
        String loginFromSession = "";

        HttpSession session = request.getSession(false);


        if (session != null) {
            loginFromSession = (String) session.getAttribute("login");
            tokenFromSession = (String) session.getAttribute("token");
            User userToCompare = UserDAO.userIsExist(loginFromSession) ?
                    UserDAO.findUserByLogin(loginFromSession) : new User("", "no token", 0);
            if (tokenFromSession.equals(userToCompare.getGitHubToken())) {
                log.info("Access granted");
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                log.warn("Unauthorized access request (inner)");
                response.sendRedirect("/RandomNumberClientServerApp-1.0-SNAPSHOT/index.html");
            }
        } else {
            log.warn("Unauthorized access request (outer)");
            response.sendRedirect("/RandomNumberClientServerApp-1.0-SNAPSHOT/index.html");
        }
    }

    @Override
    public void destroy() {

    }
}
