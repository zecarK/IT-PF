package dao;

import model.User;

public interface UserDAO {

    boolean registerUser(User user);
    User loginUser(String username, String password);
}