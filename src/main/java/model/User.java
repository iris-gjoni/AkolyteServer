package model;

/**
 * Created by irisg on 31/03/2020.
 */
public class User {


    final private String username;
    private String password;
    private String location;
    private Games game;
    private boolean loggedOn;


    public User(String username, String password, String location, Games game) {
        this.username = username;
        this.password = password;
        this.location = location;
        this.game = game;
    }

//    public User parse(Object o){
//
//    }

    public String getUsername() {
        return username;
    }

    public boolean isLoggedOn() {
        return loggedOn;
    }

    public void setLoggedOn(boolean loggedOn) {
        this.loggedOn = loggedOn;
    }

    public String getPassword() {
        return password;
    }

    public String getLocation() {
        return location;
    }

    public Games getGame() {
        return game;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", location='" + location + '\'' +
                ", game=" + game +
                ", loggedOn=" + loggedOn +
                '}';
    }
}
