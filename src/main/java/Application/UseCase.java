package Application;

import users.Login;

public interface UseCase {

    public boolean isAuthorized(Login userLogin);

    public Message successData();

    public void execute();


}
