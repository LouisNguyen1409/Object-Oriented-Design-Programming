package hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class StandardRoom implements Room {
    private List<Booking> bookings = new ArrayList<Booking>();

    @Override
    public Booking book(LocalDate arrival, LocalDate departure) {
        for (Booking booking : bookings) {
            if (booking.overlaps(arrival, departure)) {
                return null;
            }
        }

        Booking booking = new Booking(arrival, departure);
        bookings.add(booking);
        return booking;
    }

    @Override
    public JSONObject toJSON() {
        // TODO
        return null;
    }

    @Override
    public void printWelcomeMessage() {
        System.out.println("Welcome to your standard room. Enjoy your stay :)");
    }

}
