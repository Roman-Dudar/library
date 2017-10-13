package org.dudar.controller.command.implementation.account;

import org.dudar.controller.command.Command;
import org.dudar.controller.constants.Page;
import org.dudar.controller.constants.Parameters;
import org.dudar.controller.util.Validator;
import org.dudar.dto.LoginDto;
import org.dudar.model.entity.User;
import org.dudar.model.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostSignUpCommand implements Command{

    private static UserService userService = UserService.getInstance();

    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = parseRequest(request);
        UserService userService = UserService.getInstance();
        userService.createUser(user);
        if (user.getId() != 0) {

        }
        return Page.SIGN_UP;
    }

    private User parseRequest(HttpServletRequest request) {
        String password = null;
        return new User.Builder().setName(request.getParameter(Parameters.NAME))
                                 .setSurname(request.getParameter(Parameters.SURNAME))
                                 .setPhoneNumber(request.getParameter(Parameters.PHONE_NUMBER))
                                 .setPassword(password).build();
    }
}
