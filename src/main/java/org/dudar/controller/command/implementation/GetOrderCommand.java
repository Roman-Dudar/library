package org.dudar.controller.command.implementation;

import org.dudar.controller.command.Command;
import org.dudar.controller.constants.Page;
import org.dudar.controller.constants.Parameters;
import org.dudar.model.entity.BookInstance;
import org.dudar.model.entity.enums.Status;
import org.dudar.model.services.BookInstanceService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetOrderCommand implements Command{

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookInstance bookInstance = getAvailableBookInstance(request);
        if (bookInstance != null) {
            request.setAttribute(Parameters.BOOK_INSTANCE, bookInstance);
        }
        return Page.ORDER;
    }

    private BookInstance getAvailableBookInstance(HttpServletRequest request) {
        int bookId = Integer.parseInt(request.getParameter(Parameters.BOOK_ID));
        BookInstanceService bookInstanceService = BookInstanceService.getInstance();
        List<BookInstance> bookInstances = bookInstanceService.getByBookDescriptionId(new Long(bookId));
        for (BookInstance bi: bookInstances) {
            if (bi.getStatus() == Status.AVAILABLE) {
                return bi;
            }
        }
        return null;
    }

}
