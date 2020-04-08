package other;

import model.Trainer;
import model.TrainingBooking;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by irisg on 29/03/2020.
 */
public class BookingSystemManager {

    /* Booking availability */
    /* Booking Tracker */
    /* Map */

    private final ArrayList<Trainer> listOfTrainers = new ArrayList<>();
    private final Map<Trainer, ArrayList<TrainingBooking>> bookings = new HashMap<>();


    BookingSystemManager(){
        init();
    }

    public void init(){
        /* load trainers, existing bookings and available times */

        File trainerFile = new File("\\Trainers");
        // file

    }

    public void addBooking(final String trainerName, final String timeSlot){
        /* check for clash */
        /* map.add */


    }

    public void removeBooking(final String trainerName, final String timeSlot){
        /* check if it exists and remove else flag serious error */


    }

    public Map<Trainer, ArrayList<TrainingBooking>> getBookings() {
        return bookings;
    }
}
