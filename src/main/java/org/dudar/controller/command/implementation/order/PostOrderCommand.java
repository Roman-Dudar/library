package org.dudar.controller.command.implementation.order;

import org.dudar.controller.command.Command;
import org.dudar.controller.constants.Page;
import org.dudar.controller.constants.Parameters;
import org.dudar.model.entity.BookInstance;
import org.dudar.model.entity.User;
import org.dudar.model.services.BookInstanceService;
import org.dudar.model.services.BookOrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class PostOrderCommand implements Command{

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookInstanceService bookInstanceService = BookInstanceService.getInstance();
        Optional<BookInstance> bookInstance = GetOrderCommand.getAvailableBookInstance(request);
        String orderType = request.getParameter(Parameters.ORDER_TYPE);
        if (bookInstance.isPresent()) {
            BookOrderService.getInstance().orderBookInstance(bookInstance.get(),
                    (User) request.getSession().getAttribute(Parameters.USER));
        }

        return Page.ORDER;
    }

}
