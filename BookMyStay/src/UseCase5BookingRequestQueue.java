import java.util.*;

/**
 * UseCase5BookingRequestQueue
 *
 * Demonstrates booking request handling using Queue (FIFO).
 * Requests are stored in arrival order without modifying inventory.
 *
 * @author Sreeshan
 * @version 5.0
 */

// -------- RESERVATION CLASS --------
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String toString() {
        return guestName + " -> " + roomType;
    }
}

// -------- BOOKING QUEUE --------
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>(); // FIFO queue
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request Added: " + reservation);
    }

    // Display queue
    public void displayQueue() {
        System.out.println("\n------ Booking Request Queue ------");

        if (queue.isEmpty()) {
            System.out.println("No pending requests.");
        } else {
            for (Reservation r : queue) {
                System.out.println(r);
            }
        }

        System.out.println("----------------------------------");
    }
}

// -------- MAIN --------
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay - Booking Requests");
        System.out.println("=======================================");
        System.out.println("Version: 5.0\n");

        // Initialize queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate incoming requests (FIFO order)
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));
        bookingQueue.addRequest(new Reservation("David", "Single Room"));

        // Display queue
        bookingQueue.displayQueue();

        System.out.println("\nNote: Requests are stored in FIFO order.");
        System.out.println("No allocation or inventory update done.");

        System.out.println("=======================================");
        System.out.println("Application Finished");
    }
}