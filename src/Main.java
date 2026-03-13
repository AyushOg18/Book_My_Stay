import java.util.*;

// 1. Add-On Service - Represents an individual offering
class AddOnService {
    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() { return serviceName; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return serviceName + " (₹" + price + ")";
    }
}

// 2. Add-On Service Manager - Manages the association
class AddOnManager {
    // Map linking Reservation ID -> List of Selected Services
    private Map<String, List<AddOnService>> reservationAddOns;

    public AddOnManager() {
        this.reservationAddOns = new HashMap<>();
    }

    // Step 1, 2 & 3: Mapping services to a Reservation ID
    public void addServicesToReservation(String reservationId, List<AddOnService> services) {
        reservationAddOns.put(reservationId, new ArrayList<>(services));
        System.out.println("System: Add-ons linked to " + reservationId);
    }

    // Step 4: Calculate additional costs
    public double calculateExtraCost(String reservationId) {
        double total = 0;
        List<AddOnService> services = reservationAddOns.getOrDefault(reservationId, new ArrayList<>());
        for (AddOnService s : services) {
            total += s.getPrice();
        }
        return total;
    }

    public void displayAddOns(String reservationId) {
        System.out.println("Add-ons for " + reservationId + ": " +
                reservationAddOns.getOrDefault(reservationId, Collections.emptyList()));
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        // Assume we have a confirmed reservation from UC 6
        String myResId = "RES-A101";

        // 1. Guest selects services
        AddOnService wifi = new AddOnService("High Speed Wifi", 500.0);
        AddOnService breakfast = new AddOnService("Buffet Breakfast", 1200.0);

        List<AddOnService> selected = Arrays.asList(wifi, breakfast);

        // 2. Initialize Manager and Map Services
        AddOnManager addOnManager = new AddOnManager();
        addOnManager.addServicesToReservation(myResId, selected);

        // 3. Display and Calculate Cost
        addOnManager.displayAddOns(myResId);
        double extraCharge = addOnManager.calculateExtraCost(myResId);

        System.out.println("Total Additional Cost: ₹" + extraCharge);
        System.out.println("\n(Note: Core inventory and booking state remain unchanged.)");
    }
}