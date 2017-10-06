package org.dudar.controller.command.implementation;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dudar.controller.command.Command;
import org.dudar.controller.constants.Page;
import org.dudar.controller.constants.Parameters;
import org.dudar.model.entity.BookDescription;
import org.dudar.model.services.BookDescriptionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetCatalogCommand implements Command{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BookDescription> books = BookDescriptionService.getInstance().getBookDescription(25, 0);
        Logger logger = LogManager.getLogger(GetCatalogCommand.class);
        for (BookDescription book: books) {
            logger.info(book.toString());
        }
        request.setAttribute(Parameters.BOOKS, books);
        return Page.CATALOG;
    }

}
