package daoimpl;

import dao.UserDAO;
import database.DBConnection;
import model.User;

import java.sql.*;

public class UserDAOImpl implements UserDAO {

    Connection con = DBConnection.getConnection();

    @Override
    public boolean registerUser(User user) {

        String sql = "INSERT INTO users(full_name, username, password, profile_picture, sex) VALUES(?,?,?,?,?)";

        try {
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, user.getFullName());
            pst.setString(2, user.getUsername());
            pst.setString(3, user.getPassword());
            pst.setBytes(4, user.getProfilePicture());
            pst.setString(5, user.getGender());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public User loginUser(String username, String password) {

        String sql = "SELECT * FROM users WHERE username=? AND password=?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setGender(rs.getString("sex"));
                user.setProfilePicture(
                    rs.getBytes("profile_picture")
            );
                return user;
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}