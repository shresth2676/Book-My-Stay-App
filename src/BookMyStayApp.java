import java.util.*;

/**
 * Book My Stay Application
 *
 * Use Case 4: Room Search & Availability Check
 *
 * Demonstrates read-only search of available rooms without
 * modifying the inventory state.
 *
 * @author Chiranjeev
 * @version 4.1
 */


/* ---------------- ABSTRACT ROOM CLASS ---------------- */

abstract class Room {

    private String type;
    private int beds;
    private double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }

    public void displayDetails() {
        System.out.println("Room Type : " + type);
        System.out.println("Beds      : " + beds);
        System.out.println("Price     : ₹" + price);
    }
}


/* ---------------- ROOM TYPES ---------------- */

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1500);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 2500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 5000);
    }
}


/* ---------------- INVENTORY CLASS ---------------- */

class RoomInventory {

    private HashMap<String, Integer> availability;

    public RoomInventory() {

        availability = new HashMap<>();

        availability.put("Single Room", 5);
        availability.put("Double Room", 0);
        availability.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }
}


/* ---------------- SEARCH SERVICE ---------------- */

class RoomSearchService {

    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms(List<Room> rooms) {

        System.out.println("\n---- Available Rooms ----");

        for (Room room : rooms) {

            int count = inventory.getAvailability(room.getType());

            // Defensive check: show only available rooms
            if (count > 0) {

                room.displayDetails();
                System.out.println("Available : " + count);
                System.out.println("--------------------------");

            }
        }
    }
}


/* ---------------- MAIN APPLICATION ---------------- */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("        Book My Stay System         ");
        System.out.println("            Version 4.1             ");
        System.out.println("====================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create room objects
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Search service
        RoomSearchService searchService = new RoomSearchService(inventory);

        // Guest performs search
        searchService.searchAvailableRooms(rooms);

        System.out.println("\nSearch completed successfully.");
    }
}