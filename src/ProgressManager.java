import java.util.Scanner;

class ProgressManager {
    private final HealthTracker healthTracker;
    private final UIManager uiManager;
    private final Scanner scanner;

    public ProgressManager(HealthTracker healthTracker, UIManager uiManager) {
        this.healthTracker = healthTracker;
        this.uiManager = uiManager;
        this.scanner = new Scanner(System.in);
    }

    public void viewProgressAndStatistics() {
        boolean exit = false;
        while (!exit) {
            displayStatisticsMenu();
            int choice = getStatisticsMenuChoice();
            
            switch (choice) {
                case 1:
                    healthTracker.displayActivityProgress();
                    break;
                case 2:
                    healthTracker.displayWellnessGoalsProgress();
                    break;
                case 3:
                    healthTracker.displayStreaksInfo();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    uiManager.showErrorMessage("Invalid choice. Please try again.");
            }
            
            if (!exit) {
                waitForUserToContinue();
            }
        }
    }
    
    private void displayStatisticsMenu() {
        System.out.println("\n\u001B[33m============ 🏆 Statistics Menu 🏆 ============\u001B[0m");
        System.out.println("1. View Activity Progress");
        System.out.println("2. View Wellness Goals Progress");
        System.out.println("3. View Streaks and Daily Stats");
        System.out.println("4. Return to Main Menu");
        uiManager.displayPrompt("Enter your choice: ");
    }
    
    private int getStatisticsMenuChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void waitForUserToContinue() {
        uiManager.promptEnterToContinue();
        scanner.nextLine();
    }
}
