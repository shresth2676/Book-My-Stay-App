import java.io.*;
import java.util.*;

/*
 Book My Stay Application

 Use Case 12: Data Persistence & System Recovery
 Demonstrates saving system state to file and restoring it during restart.
*/


/* ---------------- RESERVATION CLASS ---------------- */

class Reservation implements Serializable {

    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}


/* ---------------- SYSTEM STATE ---------------- */

class SystemState implements Serializable {

    Map<String, Integer> inventory;
    List<Reservation> bookingHistory;

    public SystemState(Map<String,Integer> inventory, List<Reservation> history) {
        this.inventory = inventory;
        this.bookingHistory = history;
    }
}


/* ---------------- PERSISTENCE SERVICE ---------------- */

class PersistenceService {

    private static final String FILE_NAME = "hotel_state.dat";

    /* Save state to file */
    public static void saveState(SystemState state) {

        try {

            FileOutputStream fos = new FileOutputStream(FILE_NAME);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(state);

            oos.close();

            System.out.println("System state saved successfully.");

        } catch (Exception e) {

            System.out.println("Error saving system state.");
        }
    }

    /* Load state from file */
    public static SystemState loadState() {

        try {

            FileInputStream fis = new FileInputStream(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);

            SystemState state = (SystemState) ois.readObject();

            ois.close();

            System.out.println("System state restored successfully.");

            return state;

        } catch (Exception e) {

            System.out.println("No previous state found. Starting fresh.");

            return null;
        }
    }
}


/* ---------------- MAIN APPLICATION ---------------- */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" Book My Stay - Persistence Demo ");
        System.out.println("======================================");

        SystemState state = PersistenceService.loadState();

        Map<String,Integer> inventory;
        List<Reservation> history;

        /* If no saved state exists */

        if (state == null) {

            inventory = new HashMap<>();
            history = new ArrayList<>();

            inventory.put("Single", 2);
            inventory.put("Double", 1);
            inventory.put("Suite", 1);

        } else {

            inventory = state.inventory;
            history = state.bookingHistory;
        }

        /* Simulated booking */

        Reservation r = new Reservation("RES" + (history.size()+1), "Alice", "Single");

        if (inventory.get("Single") > 0) {

            inventory.put("Single", inventory.get("Single") - 1);

            history.add(r);

            System.out.println("Booking Confirmed -> " + r);
        }

        /* Display data */

        System.out.println("\nBooking History:");

        for (Reservation res : history) {
            System.out.println(res);
        }

        System.out.println("\nInventory:");

        for (Map.Entry<String,Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }

        /* Save state before shutdown */

        SystemState newState = new SystemState(inventory, history);

        PersistenceService.saveState(newState);

        System.out.println("\nSystem shutdown complete.");
    }
}