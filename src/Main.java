import java.util.HashMap;
import java.util.Map;

// Base Room class from UC 2
abstract class Room {
    String type;
    double price;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public abstract String getFeatures();
}

class StandardRoom extends Room {
    public StandardRoom() { super("Standard", 1500.0); }
    @Override public String getFeatures() { return "Twin Bed, Non-AC"; }
}

class DeluxeRoom extends Room {
    public DeluxeRoom() { super("Deluxe", 3000.0); }
    @Override public String getFeatures() { return "King Bed, AC, Wifi"; }
}

// Search Service - The "Read-Only" Logic Layer
class SearchService {
    // This service needs access to the Inventory and the Room details
    public void searchAvailableRooms(HashMap<String, Integer> inventory, Map<String, Room> roomDetails) {
        System.out.println("\n--- Available Rooms for Your Stay ---");
        boolean found = false;

        for (String type : inventory.keySet()) {
            int count = inventory.get(type);

            // UC 4 Requirement: Filter out unavailable rooms
            if (count > 0) {
                Room room = roomDetails.get(type);
                System.out.println("Room Type: " + type);
                System.out.println("  > Price: ₹" + room.price);
                System.out.println("  > Features: " + room.getFeatures());
                System.out.println("  > Status: " + count + " rooms left");
                System.out.println("------------------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("Sorry, no rooms are currently available.");
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        // 1. Initialize Inventory (From UC 3)
        HashMap<String, Integer> inventory = new HashMap<>();
        inventory.put("Standard", 5);
        inventory.put("Deluxe", 0); // This one should be filtered out!

        // 2. Initialize Room Objects (From UC 2)
        Map<String, Room> roomDetails = new HashMap<>();
        roomDetails.put("Standard", new StandardRoom());
        roomDetails.put("Deluxe", new DeluxeRoom());

        // 3. Guest Initiates Search
        SearchService searchService = new SearchService();
        searchService.searchAvailableRooms(inventory, roomDetails);

        System.out.println("\n(Search Complete: System state remains unchanged)");
    }
}