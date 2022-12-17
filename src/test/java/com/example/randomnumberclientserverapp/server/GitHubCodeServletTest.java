package com.example.randomnumberclientserverapp.server;

import com.example.randomnumberclientserverapp.GitHubCodeServlet;
import com.example.randomnumberclientserverapp.service.GitHubApi;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GitHubCodeServletTest extends Mockito {

    @Test
    public void testServlet() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        GitHubApi gitApi = mock(GitHubApi.class);

        when(gitApi.getToken()).thenReturn("1234");
        String[] dataArray = new String[2];
        dataArray[0] = "UserName";
        dataArray[1] = "0001";
        when(gitApi.returnDataFromGitHub()).thenReturn("access_token=1234&scope");
        when(gitApi.getUserData("1234")).thenReturn(dataArray);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new GitHubCodeServlet().doGet(request, response);

        verify(request, atLeast(1)).getParameter("code");
        writer.flush();
        assertTrue(stringWriter.toString().contains("1234"));
    }
}
