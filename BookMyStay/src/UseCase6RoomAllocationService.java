import java.util.*;

/**
 * UseCase6RoomAllocationService
 *
 * Demonstrates reservation confirmation and room allocation.
 * Ensures no double-booking using Set and maintains inventory consistency.
 *
 * @author Sreeshan
 * @version 6.0
 */

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
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decreaseAvailability(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory: " + inventory);
    }
}

// -------- BOOKING SERVICE --------
class BookingService {

    private Queue<Reservation> queue;
    private RoomInventory inventory;

    // Track allocated room IDs
    private Set<String> allocatedRoomIds;

    // Map roomType → allocated IDs
    private HashMap<String, Set<String>> allocationMap;

    public BookingService(Queue<Reservation> queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
        this.allocatedRoomIds = new HashSet<>();
        this.allocationMap = new HashMap<>();
    }

    // Process bookings (FIFO)
    public void processBookings() {

        System.out.println("\n------ Processing Bookings ------");

        while (!queue.isEmpty()) {

            Reservation r = queue.poll(); // FIFO
            String type = r.roomType;

            System.out.println("\nProcessing: " + r.guestName + " (" + type + ")");

            // Check availability
            if (inventory.getAvailability(type) > 0) {

                // Generate unique room ID
                String roomId = generateRoomId(type);

                // Ensure uniqueness (Set)
                while (allocatedRoomIds.contains(roomId)) {
                    roomId = generateRoomId(type);
                }

                // Store ID
                allocatedRoomIds.add(roomId);

                // Map type → IDs
                allocationMap.putIfAbsent(type, new HashSet<>());
                allocationMap.get(type).add(roomId);

                // Update inventory immediately
                inventory.decreaseAvailability(type);

                // Confirm booking
                System.out.println("Booking Confirmed!");
                System.out.println("Guest: " + r.guestName);
                System.out.println("Room ID: " + roomId);

            } else {
                System.out.println("Booking Failed! No rooms available.");
            }
        }

        System.out.println("\n------ Allocation Complete ------");
    }

    // Generate room ID
    private String generateRoomId(String type) {
        return type.substring(0, 2).toUpperCase() + "-" + (int)(Math.random() * 1000);
    }

    public void displayAllocations() {
        System.out.println("\nAllocated Rooms: " + allocationMap);
    }
}

// -------- MAIN --------
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay - Room Allocation");
        System.out.println("=======================================");
        System.out.println("Version: 6.0\n");

        // Create queue (FIFO)
        Queue<Reservation> queue = new LinkedList<>();
        queue.offer(new Reservation("Alice", "Single Room"));
        queue.offer(new Reservation("Bob", "Single Room"));
        queue.offer(new Reservation("Charlie", "Single Room")); // should fail
        queue.offer(new Reservation("David", "Suite Room"));

        // Inventory
        RoomInventory inventory = new RoomInventory();

        // Booking Service
        BookingService service = new BookingService(queue, inventory);

        // Process bookings
        service.processBookings();

        // Show results
        inventory.displayInventory();
        service.displayAllocations();

        System.out.println("\n=======================================");
        System.out.println("Application Finished");
    }
}