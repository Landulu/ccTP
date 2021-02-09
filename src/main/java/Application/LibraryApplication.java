package Application;

import borrow.XMLBorrowRepository;
import book.JsonBookRepository;
import book.LibraryService;
import users.JsonUserRepository;

import java.util.*;

public class LibraryApplication {

    Map<String, Choice> choices;
    LibraryService dao;


    public LibraryApplication() {
        this.dao = new LibraryService(
                new JsonBookRepository("src/main/resources/books.json"),
                new JsonUserRepository("src/main/resources/users.json"),
                new XMLBorrowRepository("src/main/resources/borrows.xml")
        );


        initChoices();
    }

    public void start() {
        while(true) {
            presentChoices();
            String key = readEntry();
            promptLogin();
            String login = readEntry();
            Choice selectedChoice = choices.get(key);
            Message result = selectedChoice.process(login);
            printMessage(result);
        }
    }

    private void presentResult(Message returnPrompt) {
        System.out.println(returnPrompt.getValue());
    }

    private void presentChoices() {
        System.out.println("Choisissez une opération");
        choices.forEach(this::presentChoice);
        System.out.println(String.format("%s -> %s", "0", "Quitter"));

    }

    private void presentChoice(String key, Choice choice) {
        System.out.println(String.format("%s -> %s", key, choice.getPrompt()));
    }

    private void initChoices() {
        choices = new HashMap<>();
        choices.put("1", new Choice("Obtenir la liste des livres", new GetAllBooks(this.dao)));

    }

    private String readEntry() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        if(input.equals("0")) System.exit(0);
        return input;
    }

    private void promptLogin() {
        System.out.println("Saisir votre Login");
    }

    private void printMessage(Message message) {
        System.out.println(message.getValue());
    }


}
