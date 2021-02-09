package Application;

import users.Login;
import users.User;
import users.UserRight;

import java.util.Arrays;
import java.util.List;

public class AddBook implements UseCase {

    private Message prompt;
    List<UserRight> authorizedUserRights;

    public AddBook() {
        this.prompt = new Message("Ajouter un livre");

        this.authorizedUserRights = Arrays.asList(
                UserRight.ADMIN);
    }


    @Override
    public boolean isAuthorized(Login userLogin) {
        return false;
    }

    @Override
    public Message successData() {
        return null;
    }

    @Override
    public void execute() {

    }
}
