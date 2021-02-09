package book;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;

public class JsonBookRepository implements IBookRepository {


    private String filePath;
    public JsonBookRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public ArrayList<Book> getAll() {
        ArrayList<Book> books = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(this.filePath));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray rawList = (JSONArray) jsonObject.get("books");
            for (Object o: rawList
            ) {
                JSONObject rawBook = (JSONObject) o;
                books.add(new Book(
                        rawBook.get("title").toString(),
                        rawBook.get("author").toString()
                        )
                );

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public void sync(ArrayList<Book> books) {
        // todo
    }
}
