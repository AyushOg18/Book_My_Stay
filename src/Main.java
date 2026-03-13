// Step 1: Abstraction - Defining the "Idea" of a Room
abstract class Room {
    String roomType;
    double pricePerNight;
    int availableRooms; // Simple variable for availability

    public Room(String roomType, double pricePerNight, int availableRooms) {
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.availableRooms = availableRooms;
    }

    // Abstract method: Every subclass must define how to display itself
    public abstract void displayRoomDetails();
}

// Step 2: Inheritance - Specific Room Types
class StandardRoom extends Room {
    public StandardRoom(int availability) {
        super("Standard Room", 1500.0, availability);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Type: " + roomType + " | Price: ₹" + pricePerNight + " | Available: " + availableRooms);
    }
}

class DeluxeRoom extends Room {
    public DeluxeRoom(int availability) {
        super("Deluxe Room", 3000.0, availability);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Type: " + roomType + " | Price: ₹" + pricePerNight + " | Available: " + availableRooms + " (Includes AC & Breakfast)");
    }
}

// Step 3: Application Entry
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("=== BookMyStay: Room Availability ===\n");

        // Creating Room objects as per UC 2 Flow
        Room standard = new StandardRoom(5);
        Room deluxe = new DeluxeRoom(2);

        // Printing details to console
        standard.displayRoomDetails();
        deluxe.displayRoomDetails();

        System.out.println("\nApplication Terminated.");
    }
}