import java.util.*;

/**
 * Book My Stay Application
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Demonstrates how booking requests are processed from a queue
 * and rooms are allocated while preventing duplicate assignments.
 *
 * @author Chiranjeev
 * @version 6.1
 */


/* ---------------- RESERVATION ---------------- */

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
}


/* ---------------- ROOM INVENTORY ---------------- */

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        int current = inventory.get(roomType);
        inventory.put(roomType, current - 1);
    }

    public void displayInventory() {

        System.out.println("\n--- Current Inventory ---");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {

            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}


/* ---------------- BOOKING REQUEST QUEUE ---------------- */

class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}


/* ---------------- BOOKING SERVICE ---------------- */

class BookingService {

    private RoomInventory inventory;

    // Store allocated room IDs
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Track rooms assigned per type
    private HashMap<String, Set<String>> roomAssignments = new HashMap<>();


    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }


    public void processBooking(Reservation reservation) {

        String roomType = reservation.getRoomType();

        System.out.println("\nProcessing reservation for " + reservation.getGuestName());

        int available = inventory.getAvailability(roomType);

        if (available <= 0) {

            System.out.println("No rooms available for " + roomType);
            return;
        }

        // Generate unique room ID
        String roomId = generateRoomId(roomType);

        // Ensure uniqueness
        while (allocatedRoomIds.contains(roomId)) {
            roomId = generateRoomId(roomType);
        }

        allocatedRoomIds.add(roomId);

        // Track assignment
        roomAssignments
                .computeIfAbsent(roomType, k -> new HashSet<>())
                .add(roomId);

        // Update inventory
        inventory.decrementRoom(roomType);

        System.out.println("Reservation Confirmed!");
        System.out.println("Guest : " + reservation.getGuestName());
        System.out.println("Room Type : " + roomType);
        System.out.println("Assigned Room ID : " + roomId);
    }


    private String generateRoomId(String roomType) {

        String prefix = roomType.replace(" ", "").substring(0,2).toUpperCase();

        int number = new Random().nextInt(900) + 100;

        return prefix + number;
    }
}


/* ---------------- MAIN APPLICATION ---------------- */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("        Book My Stay System           ");
        System.out.println("            Version 6.1               ");
        System.out.println("======================================");

        RoomInventory inventory = new RoomInventory();

        BookingRequestQueue queue = new BookingRequestQueue();

        BookingService bookingService = new BookingService(inventory);


        // Add booking requests
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Double Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room"));
        queue.addRequest(new Reservation("David", "Suite Room"));


        // Process requests FIFO
        while(!queue.isEmpty()) {

            Reservation r = queue.getNextRequest();

            bookingService.processBooking(r);
        }

        // Display remaining inventory
        inventory.displayInventory();
    }
}