package users;

public class User {

    private UserRight right;
    private Login login;


    public User(String login, UserRight rights) {

        this.login = new Login(login);
        this.right = rights;
    }

    public UserRight getRight() {
        return right;
    }

    public String getLogin() {
        return login.getValue();
    }
}
