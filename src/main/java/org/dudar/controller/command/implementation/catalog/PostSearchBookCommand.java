package org.dudar.controller.command.implementation.catalog;

import org.apache.log4j.LogManager;
import org.dudar.controller.command.Command;
import org.dudar.controller.constants.Page;
import org.dudar.controller.constants.Parameters;
import org.dudar.model.entity.BookDescription;
import org.dudar.model.services.BookDescriptionService;
import org.dudar.utils.Validator;
import org.dudar.utils.locale.LocaleMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PostSearchBookCommand implements Command{

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter(Parameters.BOOK_TITLE);
        request.setAttribute(Parameters.SEARCH_BY, title);
        List<BookDescription> books = null;
        if (true){//Validator.getInstance().validateName(title)) {
           books = BookDescriptionService.getInstance().getBookDescriptionByTitle(title);
        } else {
            request.setAttribute(Parameters.ERROR, LocaleMessage.INVALID_INPUT);
        }
        if (books != null && !books.isEmpty()) {
            request.setAttribute(Parameters.BOOKS, books);
        } else {
            request.setAttribute(Parameters.ERROR, LocaleMessage.BOOKS_NOT_FOUND);
        }
        return Page.SEARCH_BOOK;
    }


}
