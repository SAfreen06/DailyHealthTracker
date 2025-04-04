import java.util.Scanner;

public class HealthTrackerApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final HealthTracker healthTracker = new HealthTracker();
    private static final UIManager uiManager = new UIManager();
    private static final MenuManager menuManager = new MenuManager(scanner, healthTracker, uiManager);

    public static void main(String[] args) {
        uiManager.clearScreen();
        uiManager.displayWelcomeBanner();

        while (true) {
            menuManager.displayMainMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 6) {
                    uiManager.displayExitBanner();
                    return;
                }

                uiManager.clearScreen();

                if (!menuManager.handleMenuChoice(choice)) {
                    uiManager.showErrorMessage("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                uiManager.showErrorMessage("Please enter a valid number.");
            }
        }
    }
}