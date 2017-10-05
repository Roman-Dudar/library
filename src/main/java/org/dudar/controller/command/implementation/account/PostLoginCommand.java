package org.dudar.controller.command.implementation.account;

import org.dudar.controller.command.Command;
import org.dudar.controller.constants.Page;
import org.dudar.controller.constants.Parameters;
import org.dudar.controller.util.Validator;
import org.dudar.dto.LoginDto;
import org.dudar.model.entity.User;
import org.dudar.model.services.UserService;
import org.dudar.utils.LocaleManager;
import org.dudar.utils.LocaleMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PostLoginCommand implements Command{

    private static UserService userService = UserService.getInstance();

    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        if (session.getAttribute(Parameters.USER) != null) {
            return Page.HOME;
        }
        LoginDto loginDto = getUserInput(request);

        if (!validateUserInput(loginDto)) {
            if (loginDto.getPassword() != null)
                request.setAttribute(Parameters.USER_PHONE_NUMBER, loginDto.getPhoneNumber());
            request.setAttribute(Parameters.ERROR, LocaleManager.getString(LocaleMessage.INVALID_LOGIN_DATA));
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
        return Validator.getInstance().validatePhoneNumber(loginDto.getPhoneNumber());

    }
}
