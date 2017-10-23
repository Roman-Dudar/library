package org.dudar.controller.command.implementation.catalog;

import org.dudar.controller.command.Command;
import org.dudar.controller.constants.Page;
import org.dudar.controller.constants.Parameters;
import org.dudar.model.entity.Author;
import org.dudar.model.entity.BookDescription;
import org.dudar.model.entity.enums.Availability;
import org.dudar.utils.Validator;
import org.dudar.utils.locale.LocaleMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class PostAddBookCommand implements Command{

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (validateInput(request)) {
            // TODO: 10/23/17 add book
        }
        return Page.ADD_BOOK;
    }

    private BookDescription getUserInput(HttpServletRequest request) {
        return new BookDescription.Builder()
                .setAvailability(Availability.valueOf(request.getParameter(Parameters.AVAILABILITY).toUpperCase()))
                .setIsbn(request.getParameter(Parameters.ISBN))
                .setPublisher(request.getParameter(Parameters.PUBLISHER))
                .setGenre(request.getParameter(Parameters.GENRE))
                .setTitle(request.getParameter(Parameters.BOOK_TITLE))
                .setAuthors(parseAuthors(request)).build();
    }

    private List<Author> parseAuthors(HttpServletRequest request) {
        List<Author> authors = new LinkedList<Author>();
        String authorsStr[] = request.getParameter(Parameters.AUTHORS).split("\n");
        for (String authorStr : authorsStr) {
            String[] author = authorStr.split(" ");
            switch (author.length) {
                case 2: {
                    authors.add(new Author.Builder().setName(author[0]).setSurname(author[1]).build());
                    break;
                }
                case 3: {
                    authors.add(new Author.Builder().setName(author[0])
                            .setPatronymic(author[1]).setSurname(author[2]).build());
                    break;
                }
                default: break;
            }
        }
        return authors;
    }

    private boolean validateInput(HttpServletRequest request) {
        Validator validator = Validator.getInstance();
        String error ;
        if (validator.validateIsbn(request.getParameter(Parameters.ISBN))) {

        }
        return true;
    }

//    private String buildErrorMessage(String error){}

}
