package com.example.randomnumberclientserverapp.server;

import com.example.randomnumberclientserverapp.LoginServlet;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginServletTest extends Mockito {

    @Test
    public void testServlet() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("login")).thenReturn("Username");
        when(request.getParameter("token")).thenReturn("GitHubToken");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new LoginServlet().doPost(request, response);

        verify(request, atLeast(1)).getParameter("login");
        writer.flush();
        assertTrue(stringWriter.toString().contains("Username"));
    }
}
