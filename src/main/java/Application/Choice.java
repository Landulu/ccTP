package Application;

import users.Login;

public class Choice {

    private Message prompt;
    private Message returnPrompt;
    private UseCase operation;

    public Choice( String prompt, UseCase operation) {
        this.prompt = new Message(prompt);
        this.operation = operation;
    }

    public UseCase getOperation() {
        return operation;
    }

    public String getPrompt() {
        return prompt.getValue();
    }

    public Message getReturnPrompt() {
        return returnPrompt;
    }

    public Message process(String login) {
        if(operation.isAuthorized(new Login(login))) {
            operation.execute();
            return operation.successData();
        }
        return new Message("Identifiants invalides");
    }
}
