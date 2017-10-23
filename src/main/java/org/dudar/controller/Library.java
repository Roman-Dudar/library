package org.dudar.controller;

import org.apache.log4j.Logger;
import org.dudar.controller.command.Command;
import org.dudar.controller.command.CommandFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = { "/main/*" }, loadOnStartup = 1)
public class Library extends HttpServlet{

    private static final Logger LOGGER = Logger.getLogger(Library.class);
    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandFactory.getCommand(request);
        String resultPath = command.execute(request, response);
        request.getRequestDispatcher(resultPath).forward(request, response);
    }
}