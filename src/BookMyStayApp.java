import java.util.*;

/*
 Book My Stay Application

 Use Case 11: Concurrent Booking Simulation
 Demonstrates thread-safe booking using synchronized methods.
*/


/* ---------------- RESERVATION CLASS ---------------- */

class Reservation {

    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}


/* ---------------- ROOM INVENTORY ---------------- */

class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);
    }

    /* synchronized prevents race conditions */
    public synchronized boolean allocateRoom(String guest, String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {

            inventory.put(roomType, available - 1);

            System.out.println(Thread.currentThread().getName()
                    + " -> Booking confirmed for "
                    + guest + " | Room Type: " + roomType);

            return true;

        } else {

            System.out.println(Thread.currentThread().getName()
                    + " -> Booking failed for "
                    + guest + " (No rooms available)");

            return false;
        }
    }

    public void displayInventory() {

        System.out.println("\nFinal Inventory State:");

        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}


/* ---------------- BOOKING PROCESSOR THREAD ---------------- */

class BookingProcessor extends Thread {

    private Queue<Reservation> bookingQueue;
    private RoomInventory inventory;

    public BookingProcessor(String name, Queue<Reservation> queue, RoomInventory inventory) {
        super(name);
        this.bookingQueue = queue;
        this.inventory = inventory;
    }

    public void run() {

        while (true) {

            Reservation r;

            /* synchronized block protects queue access */
            synchronized (bookingQueue) {

                if (bookingQueue.isEmpty()) {
                    break;
                }

                r = bookingQueue.poll();
            }

            if (r != null) {
                inventory.allocateRoom(r.guestName, r.roomType);
            }
        }
    }
}


/* ---------------- MAIN APPLICATION ---------------- */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("  Book My Stay - Concurrent Booking   ");
        System.out.println("======================================");

        RoomInventory inventory = new RoomInventory();

        Queue<Reservation> bookingQueue = new LinkedList<>();

        /* Simulated concurrent booking requests */

        bookingQueue.add(new Reservation("Alice", "Single"));
        bookingQueue.add(new Reservation("Bob", "Single"));
        bookingQueue.add(new Reservation("Charlie", "Single"));
        bookingQueue.add(new Reservation("David", "Double"));
        bookingQueue.add(new Reservation("Eva", "Suite"));

        /* Multiple threads processing bookings */

        BookingProcessor t1 = new BookingProcessor("Thread-1", bookingQueue, inventory);
        BookingProcessor t2 = new BookingProcessor("Thread-2", bookingQueue, inventory);
        BookingProcessor t3 = new BookingProcessor("Thread-3", bookingQueue, inventory);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.displayInventory();

        System.out.println("\nConcurrent booking simulation completed safely.");
    }
}