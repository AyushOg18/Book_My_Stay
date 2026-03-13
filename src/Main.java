import java.util.Scanner;

public class BookMyStayApp {

    public static void main(String[] args) {
        // Initialize the welcome message and entry point
        displayWelcomeMessage();

        // Potential next step: Displaying a menu or asking for user input
        showMainMenu();
    }

    /**
     * Requirement: UC 1 - Application Entry & Welcome Message
     */
    public static void displayWelcomeMessage() {
        System.out.println("=============================================");
        System.out.println("      WELCOME TO THE BOOK MY STAY APP        ");
        System.out.println("      Your Comfort, Our Priority!            ");
        System.out.println("=============================================");
        System.out.println("System Initialized... Ready to assist you.\n");
    }

    public static void showMainMenu() {
        System.out.println("How can we help you today?");
        System.out.println("1. View Available Hotels");
        System.out.println("2. Check Booking Status");
        System.out.println("3. Exit");
        System.out.print("\nPlease enter your choice: ");
    }
}