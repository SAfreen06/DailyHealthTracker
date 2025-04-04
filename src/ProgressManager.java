import java.util.Scanner;

class ProgressManager {

    private final HealthTracker healthTracker;
    private final UIManager uiManager;

    public ProgressManager(HealthTracker healthTracker, UIManager uiManager) {
        this.healthTracker = healthTracker;
        this.uiManager = uiManager;
    }

    public void viewProgressAndStatistics() {
        healthTracker.displayDailyStatistics();
        waitForUserToContinue();
    }

    private void waitForUserToContinue() {
        uiManager.promptEnterToContinue();
        Scanner scanner=new Scanner(System.in);
        scanner.nextLine();
    }
}
