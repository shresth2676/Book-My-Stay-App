import java.util.*;

/*
 Book My Stay Application

 Use Case 10: Booking Cancellation & Inventory Rollback
 Demonstrates cancellation of confirmed bookings and restoring inventory
 using Stack for rollback logic.
*/


/* ---------------- RESERVATION CLASS ---------------- */

class Reservation {

    String reservationId;
    String guestName;
    String roomType;
    String roomId;
    boolean active;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.active = true;
    }
}


/* ---------------- ROOM INVENTORY ---------------- */

class RoomInventory {

    Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void increment(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String,Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}


/* ---------------- BOOKING SERVICE ---------------- */

class BookingService {

    Map<String, Reservation> reservations = new HashMap<>();
    Stack<String> rollbackStack = new Stack<>();
    RoomInventory inventory;

    int reservationCounter = 1;
    int roomCounter = 101;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public String confirmBooking(String guest, String roomType) {

        if (!inventory.isAvailable(roomType)) {
            System.out.println("Booking Failed: No rooms available for " + roomType);
            return null;
        }

        String reservationId = "RES" + reservationCounter++;
        String roomId = roomType.substring(0,1) + roomCounter++;

        Reservation r = new Reservation(reservationId, guest, roomType, roomId);

        reservations.put(reservationId, r);

        inventory.decrement(roomType);

        System.out.println("Booking Confirmed -> " + reservationId +
                " | Guest: " + guest +
                " | Room: " + roomId);

        return reservationId;
    }

    public void cancelBooking(String reservationId) {

        Reservation r = reservations.get(reservationId);

        if (r == null || !r.active) {
            System.out.println("Cancellation Failed: Reservation not found or already cancelled.");
            return;
        }

        rollbackStack.push(r.roomId);

        inventory.increment(r.roomType);

        r.active = false;

        System.out.println("Booking Cancelled -> " + reservationId +
                " | Released Room: " + r.roomId);
    }

    public void showRollbackStack() {

        System.out.println("\nRollback Stack (Released Rooms):");

        for (String room : rollbackStack) {
            System.out.println(room);
        }
    }
}


/* ---------------- MAIN CLASS ---------------- */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("      Book My Stay - Version 10     ");
        System.out.println("====================================");

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        String r1 = service.confirmBooking("Alice", "Single");
        String r2 = service.confirmBooking("Bob", "Double");

        inventory.displayInventory();

        service.cancelBooking(r1);

        inventory.displayInventory();

        service.showRollbackStack();

        service.cancelBooking(r1);
    }
}