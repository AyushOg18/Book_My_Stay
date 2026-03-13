import java.io.*;
import java.util.*;

// 1. Make sure all domain objects are Serializable
// Apply 'implements Serializable' to RoomInventory, Reservation, etc.

class PersistenceService {
    private static final String STORAGE_FILE = "hotel_state.ser";

    // Step 2 & 3: Serialization & Writing to file
    public void saveSystemState(RoomInventory inventory, List<Reservation> history) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STORAGE_FILE))) {
            Map<String, Object> state = new HashMap<>();
            state.put("inventory", inventory);
            state.put("history", history);

            oos.writeObject(state);
            System.out.println("System: State successfully persisted to " + STORAGE_FILE);
        } catch (IOException e) {
            System.err.println("Error: Persistence failed - " + e.getMessage());
        }
    }

    // Step 5 & 6: Loading & Restoring state
    @SuppressWarnings("unchecked")
    public Map<String, Object> loadSystemState() {
        File file = new File(STORAGE_FILE);
        if (!file.exists()) {
            System.out.println("System: No previous state found. Starting fresh.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            System.out.println("System: Previous state found. Restoring data...");
            return (Map<String, Object>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error: Recovery failed - " + e.getMessage());
            return null;
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        PersistenceService persistence = new PersistenceService();
        RoomInventory inventory;
        List<Reservation> history;

        // Step 4 & 5: Restart Logic (Try to load)
        Map<String, Object> restoredState = persistence.loadSystemState();

        if (restoredState != null) {
            inventory = (RoomInventory) restoredState.get("inventory");
            history = (List<Reservation>) restoredState.get("history");
        } else {
            // Fresh Initialization if no file exists
            inventory = new RoomInventory();
            inventory.registerRoom("Standard", 10);
            history = new ArrayList<>();
        }

        // --- Simulate App Usage ---
        System.out.println("Current Inventory: ");
        inventory.displayInventory();

        // Step 1: Prepare for Shutdown (Simulate)
        System.out.println("\nAction: Shutting down system...");
        persistence.saveSystemState(inventory, history);
        System.out.println("Application Terminated Safely.");
    }
}