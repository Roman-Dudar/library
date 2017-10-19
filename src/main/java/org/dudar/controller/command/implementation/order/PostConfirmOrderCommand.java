package org.dudar.controller.command.implementation.order;

import org.dudar.controller.command.Command;
import org.dudar.controller.constants.Page;
import org.dudar.controller.constants.Parameters;
import org.dudar.model.entity.BookOrder;
import org.dudar.model.services.BookOrderService;
import org.dudar.utils.LocaleMessage;
import org.dudar.utils.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class PostConfirmOrderCommand implements Command{

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter(Parameters.ORDER_ID);
        if (Validator.getInstance().validateId(idStr)) {
            Optional<BookOrder> order = BookOrderService.getInstance().getById(Long.parseLong(idStr));
            if (order.isPresent()) {
                request.setAttribute(Parameters.BOOK_ORDER, order.get());
                confirmOrder(request, order.get());
                request.setAttribute(Parameters.SUCCESS, LocaleMessage.SUCCESS);
                return Page.FIND_ORDER;
            } else {
                request.setAttribute(Parameters.ERROR, LocaleMessage.ORDER_NOT_FOUND);
            }
        }
        return Page.CONFIRM_ORDER;
    }

    private void confirmOrder(HttpServletRequest request, BookOrder bookOrder) {
        String option = request.getParameter(Parameters.CONFIRM);
        if (option != null && !option.isEmpty()) {
            if (option.equals(Parameters.PICK_UP)) {
                BookOrderService.getInstance().confirmPickUp(bookOrder);
            }
            if (option.equals(Parameters.RETURN)) {
                BookOrderService.getInstance().confirmReturn(bookOrder);
            }
        }
    }
}
