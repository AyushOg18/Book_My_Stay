import java.util.LinkedList;
import java.util.Queue;

// 1. Reservation - Represents a guest's intent
class BookingRequest {
    private String guestName;
    private String roomType;
    private int numberOfNights;

    public BookingRequest(String guestName, String roomType, int numberOfNights) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.numberOfNights = numberOfNights;
    }

    @Override
    public String toString() {
        return "[Guest: " + guestName + " | Room: " + roomType + " | Stay: " + numberOfNights + " nights]";
    }
}

// 2. Booking Request Queue - Manages and orders incoming requests
class BookingQueue {
    // Using LinkedList as the underlying structure for the Queue
    private Queue<BookingRequest> requestQueue;

    public BookingQueue() {
        this.requestQueue = new LinkedList<>();
    }

    // Step 2 & 3: Add request and preserve arrival order
    public void submitRequest(BookingRequest request) {
        requestQueue.add(request);
        System.out.println("System: Request received from " + request);
    }

    // Step 5: Display state (No inventory mutation occurs here)
    public void displayQueueStatus() {
        System.out.println("\n--- Current Booking Queue (Waiting for Allocation) ---");
        if (requestQueue.isEmpty()) {
            System.out.println("Queue is empty.");
        } else {
            for (BookingRequest req : requestQueue) {
                System.out.println("Pending -> " + req);
            }
        }
        System.out.println("------------------------------------------------------");
    }

    // This will be used in the next UC to process the requests
    public BookingRequest getNextRequest() {
        return requestQueue.poll();
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        // Initialize the Queue System
        BookingQueue hotelQueue = new BookingQueue();

        // Step 1: Guests submit booking requests
        System.out.println("Action: Guests are submitting requests...\n");

        hotelQueue.submitRequest(new BookingRequest("Alice", "Deluxe", 2));
        hotelQueue.submitRequest(new BookingRequest("Bob", "Standard", 1));
        hotelQueue.submitRequest(new BookingRequest("Charlie", "Deluxe", 3));

        // Step 4 & 5: Display current state (Order is preserved: Alice -> Bob -> Charlie)
        hotelQueue.displayQueueStatus();

        System.out.println("\nNote: Inventory has not been changed yet. Requests are only queued.");
    }
}