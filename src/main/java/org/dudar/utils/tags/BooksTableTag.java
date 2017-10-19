package org.dudar.utils.tags;

import org.dudar.model.entity.Author;
import org.dudar.model.entity.BookDescription;
import org.dudar.utils.locale.LocaleManager;
import org.dudar.utils.locale.LocaleMessage;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

/**
 * java tag class to output list of books
 */
public class BooksTableTag extends TagSupport{

    private List<BookDescription> books;
    private String contextPath;

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public void setBooks(List<BookDescription> books) {
        this.books = books;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().write(showBooks());
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    private String showBooks() {
        StringBuilder sb = new StringBuilder();
        sb
                .append(TagConstants.tableStartTag + TagConstants.tableHeadStartTag + TagConstants.tableRowStartTag)
                .append(TagConstants.tableHeaderCellStartTag + "#" + TagConstants.tableHeaderCellEndTag)
                .append(TagConstants.tableHeaderCellStartTag + LocaleManager.getString(LocaleMessage.TITLE)
                        + TagConstants.tableHeaderCellEndTag)
                .append(TagConstants.tableHeaderCellStartTag + LocaleManager.getString(LocaleMessage.AUTHORS)
                        + TagConstants.tableHeaderCellEndTag)
                .append(TagConstants.tableHeaderCellStartTag + LocaleManager.getString(LocaleMessage.PUBLISHER)
                        + TagConstants.tableHeaderCellEndTag)
                .append(TagConstants.tableHeaderCellStartTag + LocaleManager.getString(LocaleMessage.GENRE)
                        + TagConstants.tableHeaderCellEndTag)
                .append(TagConstants.tableHeaderCellStartTag + LocaleManager.getString(LocaleMessage.AVAILABILITY)
                        + TagConstants.tableHeaderCellEndTag)
                .append(TagConstants.tableRowEndTag).append(TagConstants.tableHeadEndTag)
                .append(TagConstants.tableBodyStartTag);

        for (BookDescription book : books) {
            sb.append(TagConstants.tableRowStartTag);
            sb.append(TagConstants.tableCellStartTag + book.getId() + TagConstants.tableCellEndTag)
                    .append(TagConstants.tableCellStartTag + TagConstants.linkToRequestStartTag + contextPath
                            + TagConstants.link + book.getId() + TagConstants.linkToRequestEnd
                            + book.getTitle() + TagConstants.tableCellEndTag)
                    .append(TagConstants.tableCellStartTag);

            for (Author author: book.getAuthors()) {
                sb.append(author.getName() + " " + author.getSurname() + TagConstants.nextLineTag);
            }

            sb.append(TagConstants.tableCellEndTag)
                    .append(TagConstants.tableCellStartTag + book.getPublisher() + TagConstants.tableCellEndTag)
                    .append(TagConstants.tableCellStartTag + book.getGenre() + TagConstants.tableCellEndTag)
                    .append(TagConstants.tableCellStartTag
                            + LocaleManager.getString(book.getAvailability().getLocaleKey())
                            + TagConstants.tableCellEndTag)
                    .append(TagConstants.tableRowEndTag);
        }
        sb.append(TagConstants.tableBodyEndTag).append(TagConstants.tableEndTag);
        return sb.toString();

    }
}
