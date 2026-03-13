import java.util.ArrayList;
import java.util.List;

// 1. Booking History - Maintains the record of confirmed reservations
class BookingHistory {
    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        this.confirmedReservations = new ArrayList<>();
    }

    // Step 1 & 2: Record confirmed booking
    public void recordReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    // Step 5: Retrieve all records
    public List<Reservation> getAllRecords() {
        return new ArrayList<>(confirmedReservations); // Return copy for safety
    }
}

// 2. Booking Report Service - Generates summaries
class BookingReportService {
    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    // Step 4 & 5: Generate Report
    public void generateSummaryReport() {
        List<Reservation> records = history.getAllRecords();

        System.out.println("\n========== ADMIN OPERATIONAL REPORT ==========");
        System.out.println("Total Bookings Processed: " + records.size());

        if (records.isEmpty()) {
            System.out.println("No data available for reporting.");
        } else {
            for (Reservation res : records) {
                // Formatting the report for the Admin
                System.out.println("[ID: " + res.reservationId + "] Guest: " +
                        res.guestName + " | Room: " + res.roomId);
            }
        }
        System.out.println("===============================================");
    }
}

// Updated main logic to show integration
public class BookMyStayApp {
    public static void main(String[] args) {
        // 1. Initialize History and Reporting
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService(history);

        // 2. Simulate successful confirmations (from UC 6)
        Reservation res1 = new Reservation("Alice", "Deluxe", "D101");
        Reservation res2 = new Reservation("Charlie", "Standard", "S105");

        // 3. Add to history
        history.recordReservation(res1);
        history.recordReservation(res2);

        // 4. Admin requests a report
        reportService.generateSummaryReport();
    }
}