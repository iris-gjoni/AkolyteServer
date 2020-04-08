package model;

/**
 * Created by irisg on 29/03/2020.
 */
public class Trainer {

    private String trainerName;
    private String password;
    private String location;
    private Games game;

    public Trainer(String trainerName, String password, String location, Games game) {
        this.trainerName = trainerName;
        this.password = password;
        this.location = location;
        this.game = game;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setGame(Games game) {
        this.game = game;
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

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }


}
