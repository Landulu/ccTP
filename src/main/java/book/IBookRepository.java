package book;

import java.util.ArrayList;

public interface IBookRepository {

    ArrayList<Book> getAll();

    void sync(ArrayList<Book> books);

}
