import java.util.*;

// Service to handle the Rollback logic
class CancellationService {
    private RoomInventory inventory;
    private BookingHistory history;

    public CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }

    // Step 2: Validate and Process Cancellation
    public void processCancellation(String resId) {
        System.out.println("\nInitiating Cancellation for: " + resId);

        // Find the reservation in history
        Reservation target = findReservation(resId);

        if (target != null) {
            // Step 3: Record details for rollback (Already in 'target' object)
            String roomType = target.roomType;
            String roomId = target.roomId;

            // Step 4: Increment Inventory (Rollback the decrement from UC 6)
            inventory.updateAvailability(roomType, 1);
            System.out.println("Rollback: Inventory for " + roomType + " incremented (+1).");

            // Step 5: Update history state
            target.status = "CANCELLED"; // Assuming status field added to Reservation
            System.out.println("Success: Room " + roomId + " is now vacant.");
        } else {
            // Step 3 (Error Path): Meaningful failure message
            System.out.println("Error: Cancellation failed. Reservation ID " + resId + " not found.");
        }
    }

    private Reservation findReservation(String resId) {
        for (Reservation res : history.getAllRecords()) {
            if (res.reservationId.equals(resId)) {
                return res;
            }
        }
        return null;
    }
}

// Updated Reservation Class to support state tracking
class Reservation {
    String reservationId;
    String guestName;
    String roomType;
    String roomId;
    String status; // New field for UC 10

    public Reservation(String guestName, String roomType, String roomId) {
        this.reservationId = "RES-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.status = "CONFIRMED";
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | Room: %s (%s)", status, reservationId, roomId, roomType);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        // Setup initial system state
        RoomInventory inventory = new RoomInventory();
        inventory.registerRoom("Deluxe", 2);
        BookingHistory history = new BookingHistory();

        // Simulate a confirmed booking
        Reservation res = new Reservation("John Doe", "Deluxe", "D101");
        history.recordReservation(res);
        inventory.updateAvailability("Deluxe", -1); // Room was taken

        System.out.println("Initial State:");
        inventory.displayInventory();

        // UC 10 Flow: Guest cancels
        CancellationService cancelService = new CancellationService(inventory, history);
        cancelService.processCancellation(res.reservationId);

        // Step 6: Verify restored state
        System.out.println("\nRestored State:");
        inventory.displayInventory();
        System.out.println("History: " + res);
    }
}