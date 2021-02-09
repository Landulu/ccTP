package borrow;

import book.Book;
import users.User;

import java.util.Date;

public class Borrow {

    private User user;

    private Book book;
    private Date startDate;
    private Date endDate;


    public Borrow(User user, Book book, Date startDate, Date endDate) {
        this.user = user;
        this.book = book;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
