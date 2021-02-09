package book;

import borrow.Borrow;
import borrow.IBorrowRepository;
import exceptions.UserNotFoundException;
import users.IUserRepository;
import users.Login;
import users.User;

import java.util.ArrayList;
import java.util.List;

public class LibraryService {


    ArrayList<Book> books;
    ArrayList<User> users;
    ArrayList<Borrow> borrows;

    IBookRepository bookRepository;
    IUserRepository userRepository;
    IBorrowRepository borrowRepository;

    public LibraryService(IBookRepository bookRepository,
                          IUserRepository userRepository,
                          IBorrowRepository borrowRepository) {

        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.borrowRepository = borrowRepository;

        init();
    }


    public void init() {
        this.books = bookRepository.getAll();
        this.users = userRepository.getAll();
        this.borrows = borrowRepository.getAll();
    }

    public List<Book> getAllBooks() {
        return this.books;
    }

    public void addBook(Book newBook) {
        this.books.add(newBook);
    }

    public void syncBooks() {
        bookRepository.sync(this.books);
    }


    public List<User> getAllUsers() { return this.users; }

    public User getUser(Login seekedlogin) throws UserNotFoundException {
        return users.stream()
                .filter(user -> isUsersLogin(user, seekedlogin))
                .findFirst()
                .orElseThrow(UserNotFoundException::new);
    }

    private boolean  isUsersLogin(User user, Login login) {
        String userLoginValue = user.getLogin();
        return userLoginValue.equals(login.getValue());
    }
}
