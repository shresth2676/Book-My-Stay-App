import java.util.*;

/**
 * Book My Stay Application
 *
 * Use Case 7: Add-On Service Selection
 *
 * Demonstrates how optional services can be attached
 * to an existing reservation without modifying booking
 * or inventory logic.
 *
 * @author Chiranjeev
 * @version 7.1
 */


/* ---------------- ADD-ON SERVICE ---------------- */

/**
 * Represents an optional hotel service.
 * @version 7.0
 */
class AddOnService {

    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

    public void displayService() {
        System.out.println(serviceName + " - ₹" + price);
    }
}


/* ---------------- SERVICE MANAGER ---------------- */

/**
 * Manages mapping between reservations and selected services.
 * @version 7.0
 */
class AddOnServiceManager {

    // Map<ReservationID, List of Services>
    private Map<String, List<AddOnService>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {

        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println(service.getServiceName() + " added to Reservation " + reservationId);
    }

    public void displayServices(String reservationId) {

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("\nServices for Reservation " + reservationId);

        for (AddOnService service : services) {
            service.displayService();
        }
    }

    public double calculateTotalCost(String reservationId) {

        double total = 0;

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services != null) {

            for (AddOnService s : services) {
                total += s.getPrice();
            }
        }

        return total;
    }
}


/* ---------------- MAIN APPLICATION ---------------- */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("        Book My Stay System           ");
        System.out.println("            Version 7.1               ");
        System.out.println("======================================");

        // Example reservation ID (from previous booking)
        String reservationId = "RSV1001";

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Guest selects services
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 1200);
        AddOnService spa = new AddOnService("Spa Access", 2000);

        serviceManager.addService(reservationId, breakfast);
        serviceManager.addService(reservationId, airportPickup);
        serviceManager.addService(reservationId, spa);

        // Display selected services
        serviceManager.displayServices(reservationId);

        // Calculate additional cost
        double totalCost = serviceManager.calculateTotalCost(reservationId);

        System.out.println("\nTotal Add-On Cost : ₹" + totalCost);

        System.out.println("\nAdd-on services processed successfully.");
    }
}