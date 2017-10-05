package org.dudar.model.entity;

import org.dudar.model.entity.enums.Status;

public class BookInstance {
    private Long id;
    private BookDescription bookDescription;
    private Status status;

    public BookInstance(Long id){
        this.id = id;
    }

    public BookInstance(Long id, BookDescription bookDescription, Status status) {
        this.id = id;
        this.bookDescription = bookDescription;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookDescription getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(BookDescription bookDescription) {
        this.bookDescription = bookDescription;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status Status) {
        this.status = Status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookInstance)) return false;

        BookInstance that = (BookInstance) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (bookDescription != null ? !bookDescription.equals(that.bookDescription) : that.bookDescription != null) return false;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (bookDescription != null ? bookDescription.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BookInstance{");
        sb.append("id=").append(id);
        sb.append(", bookDescription=").append(bookDescription);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }

    public static class Builder implements IBuilder<BookInstance> {
        private Long id;
        private BookDescription bookDescription;
        private Status status;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setBookDescription(BookDescription bookDescription) {
            this.bookDescription = bookDescription;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public BookInstance build() {
            return new BookInstance(id, bookDescription, status);
        }
    }
}