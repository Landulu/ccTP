package Application;

import exceptions.UserNotFoundException;
import book.Book;
import book.LibraryService;
import users.Login;
import users.User;
import users.UserRight;

import java.util.Arrays;
import java.util.List;

public class GetAllBooks implements UseCase {

    private Message successData;
    List<UserRight> authorizedUserRights;
    private LibraryService dao;

    public GetAllBooks(LibraryService dao) {
        this.dao = dao;
        this.successData = new Message("");

        this.authorizedUserRights = Arrays.asList(
                UserRight.ADMIN,
                UserRight.GUEST,
                UserRight.MEMBER);
    }


    @Override
    public boolean isAuthorized(Login userLogin) {
        try {
            User user = dao.getUser(userLogin);
            return authorizedUserRights.contains(user.getRight());
        } catch (UserNotFoundException e) {
            return false;
        }
    }

    @Override
    public Message successData() {
        return successData;
    }

    @Override
    public void execute() {
        List<Book> books = dao.getAllBooks();
        createDataMessage(books);
    }

    private void createDataMessage(List<Book> books) {
        successData.setList(books);
    }


}
