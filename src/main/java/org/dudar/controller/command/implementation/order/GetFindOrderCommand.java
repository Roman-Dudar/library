package org.dudar.controller.command.implementation.order;

import org.dudar.controller.command.Command;
import org.dudar.controller.constants.Page;
import org.dudar.controller.constants.Parameters;
import org.dudar.model.entity.BookInstance;
import org.dudar.model.services.BookInstanceService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class GetFindOrderCommand implements Command{

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<BookInstance> bookInstance = getAvailableBookInstance(request);
        request.setAttribute(Parameters.BOOK_INSTANCE, bookInstance.orElse(null));
        return Page.ORDER;
    }

    static Optional<BookInstance> getAvailableBookInstance(HttpServletRequest request) {
        Long bookId = Long.parseLong(request.getParameter(Parameters.BOOK_ID));
        Optional<BookInstance> bookInstance = BookInstanceService.getInstance()
                .getAvailableByBookDescriptionId(bookId);
        return bookInstance;
    }

}
