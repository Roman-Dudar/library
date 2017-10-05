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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
        Command command = CommandFactory.getCommand(request);
//        try {
        String resultPath = command.execute(request, response);
        request.getRequestDispatcher(resultPath).forward(request, response);
//        } catch (ServerAppException e) {
//            errorPageRedirection(request, response, e);
//        }
    }
}
