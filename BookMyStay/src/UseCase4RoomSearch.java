import java.util.*;

/**
 * UseCase4RoomSearch
 *
 * Demonstrates read-only room search using centralized inventory.
 * Only available rooms are displayed without modifying system state.
 *
 * @author Sreeshan
 * @version 4.0
 */

// -------- ROOM DOMAIN --------
abstract class Room {
    protected String type;
    protected double price;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public abstract void displayDetails();
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 2000);
    }

    public void displayDetails() {
        System.out.println(type + " | Price: ₹" + price);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 3500);
    }

    public void displayDetails() {
        System.out.println(type + " | Price: ₹" + price);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 6000);
    }

    public void displayDetails() {
        System.out.println(type + " | Price: ₹" + price);
    }
}

// -------- INVENTORY (READ-ONLY ACCESS) --------
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // unavailable
        inventory.put("Suite Room", 2);
    }

    // Read-only access
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Set<String> getAllRoomTypes() {
        return inventory.keySet();
    }
}

// -------- SEARCH SERVICE --------
class RoomSearchService {

    private RoomInventory inventory;
    private Map<String, Room> roomMap;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;

        // Room domain objects
        roomMap = new HashMap<>();
        roomMap.put("Single Room", new SingleRoom());
        roomMap.put("Double Room", new DoubleRoom());
        roomMap.put("Suite Room", new SuiteRoom());
    }

    // Read-only search
    public void searchAvailableRooms() {
        System.out.println("------ Available Rooms ------");

        for (String type : inventory.getAllRoomTypes()) {
            int available = inventory.getAvailability(type);

            // Defensive check → only show available rooms
            if (available > 0) {
                Room room = roomMap.get(type);

                room.displayDetails();
                System.out.println("Available: " + available + "\n");
            }
        }

        System.out.println("-----------------------------");
    }
}

// -------- MAIN --------
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay - Room Search");
        System.out.println("=======================================");
        System.out.println("Version: 4.0\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Search service
        RoomSearchService searchService = new RoomSearchService(inventory);

        // Perform search (read-only)
        searchService.searchAvailableRooms();

        System.out.println("=======================================");
        System.out.println("Search Completed (No State Changed)");
    }
}