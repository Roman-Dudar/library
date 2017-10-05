package org.dudar.model.entity;

import org.dudar.model.entity.enums.Availability;

import java.util.List;

public class BookDescription {
    private Long id;
    private String isbn;
    private String title;
    private String publisher;
    private String genre;
    private Availability availability;

    private List<Author> authors;

    public BookDescription(Long id) {
        this.id = id;
    }

    public BookDescription(Long id, String isbn, String title, String publisher,
                           List<Author> authors, Availability availability, String genre) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.publisher = publisher;
        this.genre = genre;
        this.authors = authors;
        this.availability = availability;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

    public void removeAuthor(Author author) {
        this.authors.remove(author);
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookDescription)) return false;

        BookDescription that = (BookDescription) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (isbn != null ? !isbn.equals(that.isbn) : that.isbn != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (publisher != null ? !publisher.equals(that.publisher) : that.publisher != null) return false;
        if (genre != null ? !genre.equals(that.genre) : that.genre != null) return false;
        if (availability != that.availability) return false;
        return authors != null ? authors.equals(that.authors) : that.authors == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (availability != null ? availability.hashCode() : 0);
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BookDescription{");
        sb.append("id=").append(id);
        sb.append(", isbn='").append(isbn).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", publisher='").append(publisher).append('\'');
        sb.append(", genre='").append(genre).append('\'');
        sb.append(", availability=").append(availability);
        sb.append(", authors=").append(authors);
        sb.append('}');
        return sb.toString();
    }

    public static class Builder implements IBuilder<BookDescription> {
        private Long id;
        private String isbn;
        private String title;
        private String publisher;
        private String genre;
        private Availability availability;

        private List<Author> authors;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setIsbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setPublisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder setGenre(String genre){
            this.genre = genre;
            return this;
        }

        public Builder setAuthors(List<Author> authors) {
            this.authors = authors;
            return this;
        }

        public Builder setAvailability(Availability availability) {
            this.availability = availability;
            return this;
        }

        public BookDescription build() {
            return new BookDescription(id, isbn, title, publisher, authors, availability, genre);
        }
    }
    
}
