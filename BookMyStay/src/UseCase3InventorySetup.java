import java.util.HashMap;
import java.util.Map;

/**
 * UseCase3InventorySetup
 *
 * Demonstrates centralized room inventory management using HashMap.
 * Replaces scattered availability variables with a single source of truth.
 *
 * @author Sreeshan
 * @version 3.1
 */

// -------- INVENTORY CLASS --------
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor → initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initial room availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (controlled)
    public void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
        } else {
            System.out.println("Room type not found!");
        }
    }

    // Display all inventory
    public void displayInventory() {
        System.out.println("------ Current Room Inventory ------");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println("------------------------------------");
    }
}

// -------- MAIN CLASS --------
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay - Inventory System");
        System.out.println("=======================================");
        System.out.println("Version: 3.1\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Get availability
        System.out.println("\nChecking availability for Single Room:");
        System.out.println("Available: " + inventory.getAvailability("Single Room"));

        // Update availability
        System.out.println("\nUpdating availability...");
        inventory.updateAvailability("Single Room", 4);

        // Display updated inventory
        System.out.println("\nAfter Update:");
        inventory.displayInventory();

        System.out.println("=======================================");
        System.out.println("Application Finished");
    }
}