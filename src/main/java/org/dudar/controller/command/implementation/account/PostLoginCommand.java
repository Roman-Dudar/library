package org.dudar.controller.command.implementation.account;

import org.dudar.controller.command.Command;
import org.dudar.controller.constants.Page;
import org.dudar.controller.constants.Parameters;
import org.dudar.utils.Validator;
import org.dudar.utils.dto.LoginDto;
import org.dudar.model.entity.User;
import org.dudar.model.services.UserService;
import org.dudar.utils.locale.LocaleMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostLoginCommand implements Command{

    private static UserService userService = UserService.getInstance();

    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getAttribute(Parameters.USER) != null) {
            return Page.HOME;
        }

        LoginDto loginDto = getUserInput(request);

        if (!validateUserInput(loginDto)) {
            request.setAttribute(Parameters.ERROR, LocaleMessage.INVALID_LOGIN_DATA);
            return Page.LOGIN_PAGE;
        }

        User user = userService.signInUser(loginDto);
        if (user == null) {
            return Page.LOGIN_PAGE;
        }
        request.getSession().setAttribute(Parameters.USER, user);
        return Page.HOME;
    }

    private LoginDto getUserInput(HttpServletRequest request) {
        return new LoginDto(request.getParameter(Parameters.PHONE_NUMBER), request.getParameter(Parameters.PASSWORD));
    }

    private boolean validateUserInput(LoginDto loginDto) {
        return (Validator.getInstance().validatePhoneNumber(loginDto.getPhoneNumber()) &&
            loginDto.getPassword() != null && !loginDto.getPassword().isEmpty());

    }
}
