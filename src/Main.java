import java.util.*;
import java.util.concurrent.*;

// 1. Thread-Safe Room Inventory
class ThreadSafeInventory extends RoomInventory {
    // We synchronize this method to prevent Race Conditions
    @Override
    public synchronized void updateAvailability(String roomType, int change) {
        super.updateAvailability(roomType, change);
    }

    @Override
    public synchronized int getAvailability(String roomType) {
        return super.getAvailability(roomType);
    }
}

// 2. Concurrent Processor - Simulating multiple users at once
class BookingTask implements Runnable {
    private String guestName;
    private String roomType;
    private BookingService bookingService;

    public BookingTask(String name, String type, BookingService service) {
        this.guestName = name;
        this.roomType = type;
        this.bookingService = service;
    }

    @Override
    public void run() {
        // Simulating the booking request flow in a thread
        BookingRequest request = new BookingRequest(guestName, roomType, 1);
        bookingService.processRequest(request);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) throws InterruptedException {
        // Initialize Thread-Safe components
        ThreadSafeInventory inventory = new ThreadSafeInventory();
        inventory.registerRoom("Suite", 1); // ONLY 1 SUITE AVAILABLE

        BookingHistory history = new BookingHistory();
        BookingService service = new BookingService(inventory);

        System.out.println("--- Starting Concurrent Booking Simulation ---");
        System.out.println("Available Suites: " + inventory.getAvailability("Suite"));

        // Creating a Thread Pool to simulate 3 users clicking 'Book' at the same time
        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.execute(new BookingTask("Alice", "Suite", service));
        executor.execute(new BookingTask("Bob", "Suite", service));
        executor.execute(new BookingTask("Charlie", "Suite", service));

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        // Final verification
        System.out.println("\n--- Final System State ---");
        inventory.displayInventory();
    }
}