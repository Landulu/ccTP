package users;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class JsonUserRepository implements IUserRepository {

    String filePath;

    public JsonUserRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> users = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(this.filePath));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray rawList = (JSONArray) jsonObject.get("users");
            for (Object o: rawList
                 ) {
                JSONObject rawUser = (JSONObject) o;
                users.add(new User(
                        rawUser.get("login").toString(),
                        getRightsByKey(rawUser.get("rights").toString())
                        )
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    private UserRight getRightsByKey(String rightsKey) {
        if( rightsKey.equals("ADMIN")) {
            return UserRight.ADMIN;
        }
        if (rightsKey.equals("MEMBER")){
            return UserRight.MEMBER;
        }
        return UserRight.GUEST;
    }
}
