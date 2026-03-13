import java.util.*;

// Represents a Finalized Booking
class Reservation {
    String reservationId;
    String guestName;
    String roomType;
    String roomId;

    public Reservation(String guestName, String roomType, String roomId) {
        this.reservationId = "RES-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId + " | Guest: " + guestName + " | Room: " + roomType + " | Room ID: " + roomId;
    }
}

// Booking Service - The "Engine" that processes requests
class BookingService {
    private RoomInventory inventory;
    private Set<String> assignedRoomIds; // To ensure unique room IDs (Step 4)
    private int idCounter = 101; // Simple counter for Room IDs

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.assignedRoomIds = new HashSet<>();
    }

    // Step 1: Dequeue and Process
    public void processRequest(BookingRequest request) {
        System.out.println("\nProcessing: " + request);

        // Step 2: Check Availability
        if (inventory.getAvailability(request.getRoomType()) > 0) {

            // Step 3 & 4: Generate and Record Unique Room ID
            String newRoomId = request.getRoomType().substring(0, 1).toUpperCase() + idCounter++;
            assignedRoomIds.add(newRoomId);

            // Step 5: Decrement Inventory immediately
            inventory.updateAvailability(request.getRoomType(), -1);

            // Step 6: Confirm Reservation
            Reservation confirmed = new Reservation(request.getGuestName(), request.getRoomType(), newRoomId);
            System.out.println("SUCCESS: " + confirmed);
        } else {
            System.out.println("FAILED: No availability for " + request.getRoomType());
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        // Setup Inventory
        RoomInventory inventory = new RoomInventory();
        inventory.registerRoom("Deluxe", 1); // Only 1 deluxe room available!
        inventory.registerRoom("Standard", 5);

        // Setup Queue with 2 requests for Deluxe
        BookingQueue queue = new BookingQueue();
        queue.submitRequest(new BookingRequest("Alice", "Deluxe", 2));
        queue.submitRequest(new BookingRequest("Bob", "Deluxe", 1)); // This should fail

        // Initialize Booking Service
        BookingService service = new BookingService(inventory);

        // Process Queue (First-Come-First-Served)
        while (true) {
            BookingRequest next = queue.getNextRequest();
            if (next == null) break;
            service.processRequest(next);
        }

        // Final check of inventory
        System.out.println();
        inventory.displayInventory();
    }
}