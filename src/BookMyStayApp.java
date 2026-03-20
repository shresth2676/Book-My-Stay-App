import java.util.*;

/**
 * Book My Stay Application
 *
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * Demonstrates how booking requests are collected and stored
 * using a Queue to ensure fair FIFO processing.
 *
 * @author Chiranjeev
 * @version 5.1
 */


/* ---------------- RESERVATION CLASS ---------------- */

/**
 * Represents a guest booking request.
 * @version 5.0
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Guest Name : " + guestName);
        System.out.println("Requested Room : " + roomType);
    }
}


/* ---------------- BOOKING QUEUE ---------------- */

/**
 * Handles booking request ordering using FIFO.
 * @version 5.0
 */
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    public void displayQueue() {

        System.out.println("\n--- Current Booking Request Queue ---");

        if(requestQueue.isEmpty()){
            System.out.println("No booking requests.");
            return;
        }

        for(Reservation r : requestQueue){
            r.displayReservation();
            System.out.println("---------------------------");
        }
    }
}


/* ---------------- MAIN APPLICATION ---------------- */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("        Book My Stay System         ");
        System.out.println("            Version 5.1             ");
        System.out.println("====================================");

        // Initialize booking queue
        BookingRequestQueue queue = new BookingRequestQueue();

        // Guests submit booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        queue.addRequest(r1);
        queue.addRequest(r2);
        queue.addRequest(r3);

        // Display queued requests
        queue.displayQueue();

        System.out.println("\nRequests stored successfully (FIFO order maintained).");
    }
}