package org.dudar.controller.command.implementation.account;

import org.dudar.controller.command.Command;
import org.dudar.controller.constants.Page;
import org.dudar.controller.constants.Parameters;
import org.dudar.model.entity.User;
import org.dudar.model.services.UserService;
import org.dudar.utils.LocaleMessage;
import org.dudar.utils.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostSignUpCommand implements Command{

    private static UserService userService = UserService.getInstance();

    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = parseRequest(request);
        if (validateUserInput(user)) {
            createUser(user, request);
        } else request.setAttribute(Parameters.ERROR, LocaleMessage.INVALID_INPUT);
        return Page.SIGN_UP;
    }

    private User parseRequest(HttpServletRequest request) {
        return new User.Builder().setName(request.getParameter(Parameters.NAME))
                                 .setSurname(request.getParameter(Parameters.SURNAME))
                                 .setPhoneNumber(request.getParameter(Parameters.PHONE_NUMBER))
                                 .setPassword(request.getParameter(Parameters.PASSWORD)).build();
    }

    private boolean validateUserInput(User user){
        Validator v = Validator.getInstance();
        return v.validatePhoneNumber(user.getPhoneNumber()) && v.validatePassword(user.getPassword()) &&
                v.validateName(user.getName()) && v.validateName(user.getSurname());
    }

    private void createUser(User user, HttpServletRequest request) {
        UserService userService = UserService.getInstance();
        userService.createUser(user);
        if (user.getId() != null) {
            request.setAttribute(Parameters.USER_ID, user.getId());
        } else {
            request.setAttribute(Parameters.ERROR, LocaleMessage.SIGN_UP_ERROR);
        }
    }
}
