package book;

class Title {
    private String value;

    public Title(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

class Author {
    private String value;

    public Author(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

public class Book {

    private Title title;
    private Author author;

    public Book(String title, String author) {
        this.title = new Title(title);
        this.author = new Author(author);
    }

    public String getTitle() {
        return title.getValue();
    }

    public String getAuthor() {
        return author.getValue();
    }

    @Override
    public String toString() {
        return "library.Book{" +
                "title=" + title.getValue() +
                ", author=" + author.getValue() +
                '}';
    }
}
