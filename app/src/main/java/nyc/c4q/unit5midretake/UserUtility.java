package nyc.c4q.unit5midretake;

import java.util.HashMap;
import java.util.List;

import nyc.c4q.unit5midretake.model.User;

/**
 * Created by AmyRivera on 2/20/18.
 */

public class UserUtility {
    public HashMap<String, User> buildMap(List<User> userList){
        HashMap<String, User> userHashMap = new HashMap<>();
        for (User user: userList){
            userHashMap.put(user.getEmail(), user);
        }
        return userHashMap;
    }

    public User getModelFromMap(HashMap<String, User> modelMap, String email){
        return modelMap.get(email);
    }
}