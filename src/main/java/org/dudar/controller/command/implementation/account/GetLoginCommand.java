package org.dudar.controller.command.implementation.account;

import org.dudar.controller.command.Command;
import org.dudar.controller.constants.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetLoginCommand implements Command{

    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        return Page.LOGIN_PAGE;
    }

}
