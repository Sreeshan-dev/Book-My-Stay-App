import java.util.*;

/**
 * UseCase10BookingCancellation
 *
 * Demonstrates booking cancellation and rollback using Stack.
 * Ensures inventory consistency and safe state reversal.
 *
 * @author Sreeshan
 * @version 10.0
 */

// -------- RESERVATION --------
class Reservation {
    String reservationId;
    String guestName;
    String roomType;
    String roomId;
    boolean isActive;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isActive = true;
    }

    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType + " | " + roomId +
                " | Status: " + (isActive ? "Active" : "Cancelled");
    }
}

// -------- INVENTORY --------
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public void increaseAvailability(String type) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("Inventory: " + inventory);
    }
}

// -------- CANCELLATION SERVICE --------
class CancellationService {

    private Map<String, Reservation> bookingMap;
    private RoomInventory inventory;

    // Stack for rollback (LIFO)
    private Stack<String> rollbackStack;

    public CancellationService(Map<String, Reservation> bookingMap,
                               RoomInventory inventory) {
        this.bookingMap = bookingMap;
        this.inventory = inventory;
        this.rollbackStack = new Stack<>();
    }

    public void cancelBooking(String reservationId) {

        System.out.println("\nProcessing cancellation for: " + reservationId);

        // Validate reservation
        if (!bookingMap.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Reservation not found.");
            return;
        }

        Reservation r = bookingMap.get(reservationId);

        if (!r.isActive) {
            System.out.println("Cancellation Failed: Already cancelled.");
            return;
        }

        // Push roomId to stack (LIFO rollback)
        rollbackStack.push(r.roomId);

        // Restore inventory
        inventory.increaseAvailability(r.roomType);

        // Mark as cancelled
        r.isActive = false;

        System.out.println("Cancellation Successful!");
        System.out.println("Released Room ID: " + r.roomId);
    }

    public void showRollbackStack() {
        System.out.println("Rollback Stack (Recent Releases): " + rollbackStack);
    }
}

// -------- MAIN --------
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay - Cancellation System");
        System.out.println("=======================================");
        System.out.println("Version: 10.0\n");

        // Inventory
        RoomInventory inventory = new RoomInventory();

        // Booking map (simulating confirmed bookings)
        Map<String, Reservation> bookings = new HashMap<>();

        bookings.put("RES-101", new Reservation("RES-101", "Alice", "Single Room", "SI-101"));
        bookings.put("RES-102", new Reservation("RES-102", "Bob", "Double Room", "DO-201"));

        // Cancellation service
        CancellationService service = new CancellationService(bookings, inventory);

        // Perform cancellations
        service.cancelBooking("RES-101"); // valid
        service.cancelBooking("RES-999"); // invalid
        service.cancelBooking("RES-101"); // already cancelled

        // Display results
        System.out.println("\n------ Booking Status ------");
        for (Reservation r : bookings.values()) {
            System.out.println(r);
        }

        System.out.println();
        inventory.displayInventory();

        service.showRollbackStack();

        System.out.println("\n=======================================");
        System.out.println("Application Finished");
    }
}