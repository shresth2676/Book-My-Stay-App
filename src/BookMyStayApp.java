import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay Application
 *
 * Use Case 3: Centralized Room Inventory Management
 *
 * This program demonstrates centralized inventory management
 * using HashMap to maintain room availability in a single source
 * of truth.
 *
 * @author Chiranjeev
 * @version 3.1
 */

/* ---------------- ROOM INVENTORY CLASS ---------------- */

/**
 * RoomInventory class encapsulates all logic related to
 * storing and managing room availability.
 *
 * @version 3.0
 */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    /**
     * Constructor initializes room availability
     */
    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 10);
        inventory.put("Double Room", 7);
        inventory.put("Suite Room", 3);
    }

    /**
     * Retrieve availability for a specific room type
     */
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    /**
     * Update availability for a room type
     */
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    /**
     * Display full inventory
     */
    public void displayInventory() {

        System.out.println("\n------ Current Room Inventory ------");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {

            System.out.println("Room Type : " + entry.getKey());
            System.out.println("Available : " + entry.getValue());
            System.out.println("-----------------------------");
        }
    }
}

/* ---------------- APPLICATION ENTRY POINT ---------------- */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("        Book My Stay Application      ");
        System.out.println("          Hotel Booking System        ");
        System.out.println("              Version 3.1             ");
        System.out.println("======================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display current inventory
        inventory.displayInventory();

        // Example update
        System.out.println("\nUpdating availability for Double Room...");

        inventory.updateAvailability("Double Room", 5);

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("\nInventory system executed successfully.");
    }
}