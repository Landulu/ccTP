import borrow.Borrow;
import borrow.IBorrowRepository;
import exceptions.UserNotFoundException;
import book.Book;
import book.IBookRepository;
import book.LibraryService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import users.IUserRepository;
import users.Login;
import users.User;
import users.UserRight;

import java.util.*;


class FakeUserRepository implements IUserRepository {

    ArrayList<User> userData = new ArrayList<>(Arrays.asList(
            new User("thomas", UserRight.ADMIN),
            new User("benoit", UserRight.MEMBER))
    );

    @Override
    public ArrayList<User> getAll() {
        return userData;
    }
}

class FakeBorrowRepository implements IBorrowRepository {

    ArrayList<Borrow> borrowData = new ArrayList<>(Arrays.asList(
            new Borrow(
                    new User("benoit", UserRight.MEMBER),
                    new Book("clean code", "martin"),
                    new Date(2020, Calendar.JANUARY, 1),
                    new Date(2020, Calendar.JANUARY, 20))
    ));


    @Override
    public ArrayList<Borrow> getAll() {
        return borrowData;
    }

    @Override
    public void sync(ArrayList<Borrow> borrows) {
        borrowData = borrows;
    }
}

class FakeBookRepository implements IBookRepository {

    ArrayList<Book> booksData = new ArrayList<>(Arrays.asList(
            new Book("clean code", "martin"),
            new Book("terremer", "leguin"))
    );

    @Override
    public ArrayList<Book> getAll() {
        return booksData;
    }

    @Override
    public void sync(ArrayList<Book> books) {
        books.clear();
        booksData.addAll(books);
    }
}

public class LibraryServiceTest {

    private LibraryService sut;

    @Before
    public void init() {

        sut = new LibraryService(
                new FakeBookRepository(),
                new FakeUserRepository(),
                new FakeBorrowRepository());
    }

    @Test
    public void should_return_all_books() {
        List<Book> result = sut.getAllBooks();

        Assert.assertEquals(2, result.size() );
        Book firstBook = result.get(0);
        Assert.assertEquals("clean code", firstBook.getTitle() );
    }

    @Test
    public void should_add_book_to_library() {
        Book newBook = new Book("orlando", "woolf");
        sut.addBook(newBook);
        List<Book> result = sut.getAllBooks();
        Assert.assertEquals(3, result.size() );
    }

    @Test
    public void should_return_all_user() {
        List<User> res = sut.getAllUsers();
        Assert.assertEquals(2, res.size());
        User firstUser = res.get(0);
        Assert.assertEquals("thomas", firstUser.getLogin());
    }

    @Test
    public void should_find_existing_user() {
        User res = sut.getUser(new Login("benoit"));
        Assert.assertEquals("benoit", res.getLogin());
        Assert.assertEquals(UserRight.MEMBER, res.getRight());
    }

    @Test(expected = UserNotFoundException.class)
    public void should_throw_UserNotFoundException() {
        User res = sut.getUser(new Login("titi"));
    }

}
