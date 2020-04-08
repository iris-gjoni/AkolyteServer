package model;

/**
 * Created by irisg on 29/03/2020.
 */
public class TrainingBooking {


    private String trainerName;
    private String date;
    private String startTime;
    private String endTime;
    private String studentName;
    private String studentEmail;
    private String studentNumber;
    private String studentGamerAccount;


    public TrainingBooking(
            String startTime,
            String endTime,
            String studentName,
            String studentEmail,
            String studentNumber,
            String trainerName,
            String date,
            String studentGamerAccount) {
        this.endTime = endTime;
        this.startTime = startTime;
        this.studentEmail = studentEmail;
        this.studentGamerAccount = studentGamerAccount;
        this.studentName = studentName;
        this.studentNumber = studentNumber;
        this.trainerName = trainerName;
        this.date = date;
    }

    public boolean matches(TrainingBooking otherBooking) {
        if (otherBooking.getDate().equals(this.date)) {
            if (otherBooking.getStartTime().equals(this.startTime)) {
                return true;
            }
        }
        return false;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStudentGamerAccount() {
        return studentGamerAccount;
    }

    public void setStudentGamerAccount(String studentGamerAccount) {
        this.studentGamerAccount = studentGamerAccount;
    }
}
