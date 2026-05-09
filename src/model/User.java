package model;

public class User {

    private int userId;
    private String fullName;
    private String username;
    private String password;
    private byte[] profilePicture;
    private String gender;

    public User() {}

    public User(String fullName, String username, String password, byte[] profilePicture, String gender) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.profilePicture = profilePicture;
        this.gender = gender;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public byte[] getProfilePicture() {
        return profilePicture;
    }
    
    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }
    
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}