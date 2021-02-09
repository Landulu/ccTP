package Application;

import java.util.List;

public class Message {
    private String value;

    public Message(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setList(List<?extends Object> entities) {

        StringBuilder sb = new StringBuilder();
        for (Object entity: entities) {
            sb.append(System.getProperty("line.separator"));
            sb.append(entity.toString());
        }
        value = sb.toString();
    }
}
