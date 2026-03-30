import java.util.*;

/**
 * UseCase9ErrorHandlingValidation
 *
 * Demonstrates validation and error handling using custom exceptions.
 * Prevents invalid inputs and ensures system stability.
 *
 * @author Sreeshan
 * @version 9.0
 */

// -------- CUSTOM EXCEPTION --------
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// -------- RESERVATION --------
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// -------- INVENTORY --------
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 0);
        inventory.put("Suite Room", 1);
    }

    public boolean isValidRoomType(String type) {
        return inventory.containsKey(type);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decreaseAvailability(String type) throws InvalidBookingException {
        int available = getAvailability(type);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for: " + type);
        }

        inventory.put(type, available - 1);
    }
}

// -------- VALIDATOR --------
class BookingValidator {

    public static void validate(Reservation r, RoomInventory inventory)
            throws InvalidBookingException {

        // Validate null or empty input
        if (r.guestName == null || r.guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        // Validate room type
        if (!inventory.isValidRoomType(r.roomType)) {
            throw new InvalidBookingException("Invalid room type: " + r.roomType);
        }

        // Validate availability
        if (inventory.getAvailability(r.roomType) <= 0) {
            throw new InvalidBookingException("Room not available: " + r.roomType);
        }
    }
}

// -------- MAIN --------
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay - Validation System");
        System.out.println("=======================================");
        System.out.println("Version: 9.0\n");

        RoomInventory inventory = new RoomInventory();

        // Test cases (valid + invalid)
        Reservation[] requests = {
                new Reservation("Alice", "Single Room"),   // valid
                new Reservation("", "Suite Room"),         // invalid name
                new Reservation("Bob", "Deluxe Room"),     // invalid type
                new Reservation("Charlie", "Double Room")  // no availability
        };

        for (Reservation r : requests) {
            try {
                System.out.println("Processing booking for: " + r.guestName);

                // Validate first (fail-fast)
                BookingValidator.validate(r, inventory);

                // If valid → proceed
                inventory.decreaseAvailability(r.roomType);

                System.out.println("Booking Confirmed for " + r.guestName +
                        " (" + r.roomType + ")\n");

            } catch (InvalidBookingException e) {
                // Graceful failure handling
                System.out.println("Booking Failed: " + e.getMessage() + "\n");
            }
        }

        System.out.println("=======================================");
        System.out.println("Application Finished (Safe State)");
    }
}