import java.util.*;

/**
 * Book My Stay Application
 *
 * Use Case 9: Error Handling & Validation
 *
 * Demonstrates structured validation using custom exceptions
 * to prevent invalid booking requests and maintain system stability.
 *
 * @author Chiranjeev
 * @version 9.1
 */


/* ---------------- CUSTOM EXCEPTION ---------------- */

/**
 * Custom exception for invalid booking scenarios.
 * @version 9.0
 */
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}


/* ---------------- ROOM INVENTORY ---------------- */

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, -1);
    }

    public void decrementRoom(String roomType) throws InvalidBookingException {

        int available = getAvailability(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for " + roomType);
        }

        inventory.put(roomType, available - 1);
    }

    public boolean isValidRoomType(String roomType) {
        return inventory.containsKey(roomType);
    }

    public void displayInventory() {

        System.out.println("\n--- Current Inventory ---");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}


/* ---------------- VALIDATOR ---------------- */

class InvalidBookingValidator {

    private RoomInventory inventory;

    public InvalidBookingValidator(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void validate(String guestName, String roomType)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("Selected room type is currently unavailable.");
        }
    }
}


/* ---------------- BOOKING SERVICE ---------------- */

class BookingService {

    private RoomInventory inventory;
    private InvalidBookingValidator validator;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.validator = new InvalidBookingValidator(inventory);
    }

    public void processBooking(String guestName, String roomType) {

        try {

            validator.validate(guestName, roomType);

            inventory.decrementRoom(roomType);

            System.out.println("Booking confirmed for " + guestName +
                    " | Room Type: " + roomType);

        } catch (InvalidBookingException e) {

            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}


/* ---------------- MAIN APPLICATION ---------------- */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("        Book My Stay System           ");
        System.out.println("            Version 9.1               ");
        System.out.println("======================================");

        RoomInventory inventory = new RoomInventory();

        BookingService bookingService = new BookingService(inventory);

        // Valid booking
        bookingService.processBooking("Alice", "Single Room");

        // Invalid room type
        bookingService.processBooking("Bob", "Luxury Room");

        // No rooms available
        bookingService.processBooking("Charlie", "Suite Room");

        // Empty guest name
        bookingService.processBooking("", "Double Room");

        inventory.displayInventory();

        System.out.println("\nSystem continues running safely after errors.");
    }
}