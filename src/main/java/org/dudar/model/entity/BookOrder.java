package org.dudar.model.entity;

import java.sql.Date;

public class BookOrder {
    private Long id;
    private BookInstance bookInstance;
    private User user;
    private Date creationDate;
    private Date pickupDate;
    private Date returnDate;
    private Date actualReturnDate;

    public BookOrder(Long id, BookInstance bookInstance, User user,
                 Date creationDate, Date pickupDate, Date returnDate,
                 Date actualReturnDate) {
        this.id = id;
        this.bookInstance = bookInstance;
        this.user = user;
        this.creationDate = creationDate;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
        this.actualReturnDate = actualReturnDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookInstance getBookInstance() {
        return bookInstance;
    }

    public void setBookInstance(BookInstance bookInstance) {
        this.bookInstance = bookInstance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(Date actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookOrder bookOrder = (BookOrder) o;

        if (id != null ? !id.equals(bookOrder.id) : bookOrder.id != null) return false;
        if (bookInstance != null ? !bookInstance.equals(bookOrder.bookInstance) : bookOrder.bookInstance != null) return false;
        if (user != null ? !user.equals(bookOrder.user) : bookOrder.user != null) return false;
        if (creationDate != null ? !creationDate.equals(bookOrder.creationDate) : bookOrder.creationDate != null) return false;
        if (pickupDate != null ? !pickupDate.equals(bookOrder.pickupDate) : bookOrder.pickupDate != null) return false;
        if (returnDate != null ? !returnDate.equals(bookOrder.returnDate) : bookOrder.returnDate != null) return false;
        return actualReturnDate != null ? actualReturnDate.equals(bookOrder.actualReturnDate) : bookOrder.actualReturnDate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (bookInstance != null ? bookInstance.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (pickupDate != null ? pickupDate.hashCode() : 0);
        result = 31 * result + (returnDate != null ? returnDate.hashCode() : 0);
        result = 31 * result + (actualReturnDate != null ? actualReturnDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id=").append(id);
        sb.append(", bookInstance=").append(bookInstance);
        sb.append(", user=").append(user);
        sb.append(", creationDate=").append(creationDate);
        sb.append(", pickupDate=").append(pickupDate);
        sb.append(", returnDate=").append(returnDate);
        sb.append(", actualReturnDate=").append(actualReturnDate);
        sb.append('}');
        return sb.toString();
    }

    public static class Builder implements IBuilder<BookOrder> {
        private Long id;
        private BookInstance bookInstance;
        private User user;
        private Date creationDate;
        private Date pickupDate;
        private Date returnDate;
        private Date actualReturnDate;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setBookInstance(BookInstance bookInstance) {
            this.bookInstance = bookInstance;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setCreationDate(Date creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Builder setPickupDate(Date pickupDate) {
            this.pickupDate = pickupDate;
            return this;
        }

        public Builder setReturnDate(Date returnDate) {
            this.returnDate = returnDate;
            return this;
        }

        public Builder setActualReturnDate(Date actualReturnDate) {
            this.actualReturnDate = actualReturnDate;
            return this;
        }

        public BookOrder build() {
            return new BookOrder(id, bookInstance, user, creationDate, pickupDate, returnDate, actualReturnDate);
        }
    }
}
