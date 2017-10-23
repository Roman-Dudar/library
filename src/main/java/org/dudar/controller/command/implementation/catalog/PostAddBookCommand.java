package org.dudar.controller.command.implementation.catalog;

import org.dudar.controller.command.Command;
import org.dudar.controller.constants.Page;
import org.dudar.controller.constants.Parameters;
import org.dudar.model.entity.Author;
import org.dudar.model.entity.BookDescription;
import org.dudar.model.entity.enums.Availability;
import org.dudar.model.services.BookDescriptionService;
import org.dudar.utils.Validator;
import org.dudar.utils.locale.LocaleManager;
import org.dudar.utils.locale.LocaleMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class PostAddBookCommand implements Command{

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (validateInput(request)) {
            BookDescription bookDescription = getUserInput(request);
            BookDescriptionService.getInstance().addBookDescription(bookDescription);
            // TODO: 10/24/17 implement adding authors!!
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
        String[] authorsStr = request.getParameter(Parameters.AUTHORS).split("\n");
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
        Locale locale = (Locale) request.getSession().getAttribute(Parameters.LOCALE);
        String error = null;
        if (!validator.validateIsbn(request.getParameter(Parameters.ISBN))) {
            error = buildErrorMessage(error, locale, LocaleMessage.ISBN);
        }
        if (!validator.validateGenre(request.getParameter(Parameters.GENRE))) {
            error = buildErrorMessage(error, locale, LocaleMessage.GENRE);
        }
        if (!validator.validateTitle(request.getParameter(Parameters.BOOK_TITLE))) {
            error = buildErrorMessage(error, locale, LocaleMessage.TITLE);
        }
        if (!validateAuthors(request, validator)) {
            error = buildErrorMessage(error, locale, LocaleMessage.AUTHORS);
        }
        return error == null;
    }

    private boolean validateAuthors(HttpServletRequest request, Validator validator) {
        String authorsStr = request.getParameter(Parameters.AUTHORS);
        String[] authors;
        if (authorsStr == null || authorsStr.isEmpty()) {
            return false;
        }
        authors = authorsStr.split("\n");
        for (String authorStr : authors) {
            String[] author = authorStr.split(" ");
            for (String authorPart : author) {
                if (!validator.validateName(authorPart)) {
                    return false;
                }
            }
        }
        return true;
    }

    private String buildErrorMessage(String error, Locale locale, String messageKey){
        LocaleManager.setResourceBundleLocale(locale);
        if ((error == null || error.length() == 0)) {
            error = LocaleManager.getString(LocaleMessage.INVALID_INPUT) + ": "
                    + LocaleManager.getString(messageKey);
        } else {
            error += ", " + LocaleManager.getString(messageKey);
        }
        return error;
    }

}
