package org.dudar.controller.command.implementation.order;

import org.dudar.controller.command.Command;
import org.dudar.controller.constants.Page;
import org.dudar.controller.constants.Parameters;
import org.dudar.utils.Validator;
import org.dudar.model.entity.BookOrder;
import org.dudar.model.services.BookOrderService;
import org.dudar.utils.locale.LocaleMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class PostFindOrderCommand implements Command{

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (getOrder(request)) {
            return Page.CONFIRM_ORDER;
        }
        request.setAttribute(Parameters.ERROR, LocaleMessage.INVALID_INPUT);
        return Page.FIND_ORDER;
    }

    static boolean getOrder(HttpServletRequest request) {
        String idStr = request.getParameter(Parameters.ORDER_ID);
        if (Validator.getInstance().validateId(idStr)) {
            Optional<BookOrder> order = BookOrderService.getInstance().getById(Long.parseLong(idStr));
            if (order.isPresent()) {
                request.setAttribute(Parameters.BOOK_ORDER, order.get());
            } else {
                request.setAttribute(Parameters.ERROR, LocaleMessage.ORDER_NOT_FOUND);
            }
            return true;
        }
        return false;
    }

}
