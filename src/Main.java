import java.util.Scanner;

// Custom Exception for specific domain errors
class BookingValidationException extends Exception {
    public BookingValidationException(String message) {
        super(message);
    }
}

// Validator Service - Acts as a Gatekeeper
class BookingValidator {

    public static void validateRequest(String guestName, int nights, int availability)
            throws BookingValidationException {

        // 1. Validate Input Values
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new BookingValidationException("Error: Guest name cannot be empty.");
        }

        if (nights <= 0) {
            throw new BookingValidationException("Error: Stay duration must be at least 1 night.");
        }

        // 2. Validate System Constraints
        if (availability <= 0) {
            throw new BookingValidationException("Error: Selected room type is currently Sold Out.");
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        // Mock Data for testing
        String inputName = ""; // Invalid
        int inputNights = -2;  // Invalid
        int currentAvailability = 0; // Sold Out

        System.out.println("--- Initiating Booking Validation ---");

        try {
            // Step 2: System validates input values and constraints
            BookingValidator.validateRequest(inputName, inputNights, currentAvailability);

            // This part only runs if validation passes
            System.out.println("Validation Passed! Proceeding to Queue...");

        } catch (BookingValidationException e) {
            // Step 3 & 4: Error is raised and meaningful message is displayed
            System.err.println("VALIDATION FAILED: " + e.getMessage());
        }

        // Step 5: System continues running safely
        System.out.println("\nSystem Status: Online. Ready for next request.");
    }
}