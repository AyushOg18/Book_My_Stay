import java.util.HashMap;
import java.util.Map;

// The Centralized Inventory Manager
class RoomInventory {
    // Key: Room Type (String), Value: Count (Integer)
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        this.inventory = new HashMap<>();
    }

    // Step 2: Register room types with counts
    public void registerRoom(String roomType, int initialCount) {
        inventory.put(roomType, initialCount);
    }

    // Step 4: Controlled methods for updates (Booking/Cancellations)
    public void updateAvailability(String roomType, int change) {
        if (inventory.containsKey(roomType)) {
            int currentCount = inventory.get(roomType);
            inventory.put(roomType, currentCount + change);
        } else {
            System.out.println("Error: Room type '" + roomType + "' not found in inventory.");
        }
    }

    // Step 3 & 5: Retrieve and Display current state
    public void displayInventory() {
        System.out.println("----- Current Room Inventory -----");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println("Room: " + entry.getKey() + " | Available: " + entry.getValue());
        }
        System.out.println("----------------------------------");
    }
}

// Updated Application Entry
public class BookMyStayApp {
    public static void main(String[] args) {
        // Step 1: Initialize the inventory component
        RoomInventory myInventory = new RoomInventory();

        // Step 2: Register Rooms
        myInventory.registerRoom("Standard", 10);
        myInventory.registerRoom("Deluxe", 5);
        myInventory.registerRoom("Suite", 2);

        // Initial State
        myInventory.displayInventory();

        // Step 4: Simulate a booking (Update)
        System.out.println("\nAction: Booking 1 Deluxe Room...");
        myInventory.updateAvailability("Deluxe", -1);

        // Final State
        myInventory.displayInventory();
    }
}