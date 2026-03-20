abstract class Room {

    private String roomType;
    private int numberOfBeds;
    private int roomSize;
    private double pricePerNight;

    public Room(String roomType, int numberOfBeds, int roomSize, double pricePerNight) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.roomSize = roomSize;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public int getRoomSize() {
        return roomSize;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type      : " + roomType);
        System.out.println("Beds           : " + numberOfBeds);
        System.out.println("Room Size      : " + roomSize + " sq.ft");
        System.out.println("Price per Night: ₹" + pricePerNight);
    }
}

/* ---------------- SINGLE ROOM ---------------- */

class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 200, 1500);
    }
}

/* ---------------- DOUBLE ROOM ---------------- */

class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 350, 2500);
    }
}

/* ---------------- SUITE ROOM ---------------- */

class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 600, 5000);
    }
}

/* ---------------- APPLICATION ENTRY POINT ---------------- */

public class BookMyStayApp {

    /**
     * Main method - Application entry point
     */
    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("        Book My Stay Application      ");
        System.out.println("          Hotel Booking System        ");
        System.out.println("              Version 2.1             ");
        System.out.println("======================================");

        /* Room Objects (Polymorphism) */
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        /* Static Availability Variables */
        int singleRoomAvailability = 10;
        int doubleRoomAvailability = 7;
        int suiteRoomAvailability = 3;

        System.out.println("\n--- Room Details & Availability ---\n");

        singleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + singleRoomAvailability);
        System.out.println("-----------------------------------");

        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + doubleRoomAvailability);
        System.out.println("-----------------------------------");

        suiteRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + suiteRoomAvailability);
        System.out.println("-----------------------------------");

        System.out.println("\nApplication execution completed.");
    }
}