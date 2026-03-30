import java.util.*;

/**
 * UseCase11ConcurrentBookingSimulation
 *
 * Demonstrates thread-safe booking using synchronization.
 * Prevents race conditions and double booking under concurrent requests.
 *
 * @author Sreeshan
 * @version 11.0
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

// -------- INVENTORY (SHARED RESOURCE) --------
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
    }

    // synchronized critical section
    public synchronized boolean allocateRoom(String type, String guest) {

        int available = inventory.getOrDefault(type, 0);

        if (available > 0) {
            System.out.println(Thread.currentThread().getName() +
                    " allocating room to " + guest);

            inventory.put(type, available - 1);

            return true;
        } else {
            System.out.println(Thread.currentThread().getName() +
                    " failed for " + guest + " (No rooms)");

            return false;
        }
    }

    public void displayInventory() {
        System.out.println("Final Inventory: " + inventory);
    }
}

// -------- SHARED QUEUE --------
class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.offer(r);
    }

    public synchronized Reservation getRequest() {
        return queue.poll();
    }
}

// -------- WORKER THREAD --------
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(String name, BookingQueue queue, RoomInventory inventory) {
        super(name);
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {

        while (true) {

            Reservation r;

            // synchronized queue access
            synchronized (queue) {
                r = queue.getRequest();
            }

            if (r == null) break;

            // critical section → inventory update
            inventory.allocateRoom(r.roomType, r.guestName);

            try {
                Thread.sleep(100); // simulate delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// -------- MAIN --------
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay - Concurrent Booking");
        System.out.println("=======================================");
        System.out.println("Version: 11.0\n");

        // Shared resources
        BookingQueue queue = new BookingQueue();
        RoomInventory inventory = new RoomInventory();

        // Add booking requests
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room")); // may fail

        // Multiple threads (guests)
        Thread t1 = new BookingProcessor("Thread-1", queue, inventory);
        Thread t2 = new BookingProcessor("Thread-2", queue, inventory);

        // Start threads
        t1.start();
        t2.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final state
        System.out.println("\n=======================================");
        inventory.displayInventory();

        System.out.println("\nApplication Finished (Thread Safe)");
    }
}