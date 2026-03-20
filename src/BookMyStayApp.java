import java.util.*;

/**
 * Book My Stay Application
 *
 * Use Case 8: Booking History & Reporting
 *
 * Demonstrates how confirmed reservations are stored in a
 * historical record and used for reporting purposes.
 *
 * @author Chiranjeev
 * @version 8.1
 */


/* ---------------- RESERVATION ---------------- */

/**
 * Represents a confirmed booking.
 * @version 8.0
 */
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Reservation ID : " + reservationId);
        System.out.println("Guest Name     : " + guestName);
        System.out.println("Room Type      : " + roomType);
        System.out.println("-----------------------------");
    }
}


/* ---------------- BOOKING HISTORY ---------------- */

/**
 * Stores confirmed reservations in chronological order.
 * @version 8.0
 */
class BookingHistory {

    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {

        reservations.add(reservation);

        System.out.println("Reservation stored in booking history.");
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}


/* ---------------- REPORT SERVICE ---------------- */

/**
 * Generates reports from booking history.
 * @version 8.0
 */
class BookingReportService {

    public void displayAllBookings(List<Reservation> reservations) {

        System.out.println("\n--- Booking History ---");

        for (Reservation r : reservations) {
            r.displayReservation();
        }
    }

    public void generateSummary(List<Reservation> reservations) {

        Map<String, Integer> roomSummary = new HashMap<>();

        for (Reservation r : reservations) {

            String roomType = r.getRoomType();

            roomSummary.put(roomType,
                    roomSummary.getOrDefault(roomType, 0) + 1);
        }

        System.out.println("\n--- Booking Summary Report ---");

        for (Map.Entry<String, Integer> entry : roomSummary.entrySet()) {

            System.out.println(entry.getKey() + " Bookings : " + entry.getValue());
        }
    }
}


/* ---------------- MAIN APPLICATION ---------------- */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("        Book My Stay System           ");
        System.out.println("            Version 8.1               ");
        System.out.println("======================================");

        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        history.addReservation(new Reservation("RSV101", "Alice", "Single Room"));
        history.addReservation(new Reservation("RSV102", "Bob", "Double Room"));
        history.addReservation(new Reservation("RSV103", "Charlie", "Suite Room"));
        history.addReservation(new Reservation("RSV104", "David", "Single Room"));

        BookingReportService reportService = new BookingReportService();

        // Display booking history
        reportService.displayAllBookings(history.getReservations());

        // Generate summary report
        reportService.generateSummary(history.getReservations());

        System.out.println("\nBooking reporting completed successfully.");
    }
}