package cos.dstruct.klassroom.database;

import cos.dstruct.klassroom.datatypes.User;

import java.util.ArrayList;

public class UserList {

    public ArrayList<User> user_list;

    public UserList() {
        user_list = new ArrayList<>();
    }

    public void newUser(String username, String password, UserTree tree) {
        for (User user : user_list) {
            if (user.getUsername().equals(username)) {
                throw new IllegalArgumentException(
                        "Invalid username, this name is already taken.");
            }
        }

        user_list.add(new User(username, password));

    }

    public User getUser(String username) {
        for (User user : user_list) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        throw new IllegalArgumentException("No such user found, you silly goose.");
    }
}
