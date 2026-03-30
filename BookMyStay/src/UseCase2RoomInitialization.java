/**
 * UseCase2RoomInitialization
 *
 * Demonstrates basic room modeling using abstraction, inheritance,
 * and static availability representation in the Book My Stay system.
 *
 * @author Sreeshan
 * @version 2.1
 */

// -------- ABSTRACT CLASS --------
abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    // Abstract method
    public abstract void displayDetails();
}

// -------- SINGLE ROOM --------
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 2000);
    }

    @Override
    public void displayDetails() {
        System.out.println(roomType + " | Beds: " + beds + " | Price: ₹" + price);
    }
}

// -------- DOUBLE ROOM --------
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }

    @Override
    public void displayDetails() {
        System.out.println(roomType + " | Beds: " + beds + " | Price: ₹" + price);
    }
}

// -------- SUITE ROOM --------
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }

    @Override
    public void displayDetails() {
        System.out.println(roomType + " | Beds: " + beds + " | Price: ₹" + price);
    }
}

// -------- MAIN CLASS --------
public class UseCase2RoomInitialization {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay - Room Availability");
        System.out.println("=======================================");
        System.out.println("Version: 2.1\n");

        // Polymorphism (Room reference)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability (simple variables)
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display details
        single.displayDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        doubleRoom.displayDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        suite.displayDetails();
        System.out.println("Available: " + suiteAvailable + "\n");

        System.out.println("=======================================");
        System.out.println("Application Finished");
    }
}