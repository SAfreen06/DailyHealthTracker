import java.util.Scanner;

class MenuManager {
    private final Scanner scanner;
    private final HealthTracker healthTracker;
    private final UIManager uiManager;
    private final ActivityManager activityManager;
    private final GoalsManager goalsManager;
    private final ProgressManager progressManager;

    public MenuManager(Scanner scanner, HealthTracker healthTracker, UIManager uiManager) {
        this.scanner = scanner;
        this.healthTracker = healthTracker;
        this.uiManager = uiManager;
        this.activityManager = new ActivityManager(scanner, healthTracker, uiManager);
        this.goalsManager = new GoalsManager(scanner, healthTracker, uiManager);
        this.progressManager = new ProgressManager(healthTracker, uiManager);
    }

    public void displayMainMenu() {
        uiManager.displayMainMenu();
    }

    public boolean handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                activityManager.addNewActivity();
                return true;
            case 2:
                activityManager.logActivityProgress();
                return true;
            case 3:
                activityManager.editOrDeleteActivities();
                return true;
            case 4:
                goalsManager.setDailyHealthGoals();
                return true;
            case 5:
                progressManager.viewProgressAndStatistics();
                return true;
            default:
                return false;
        }
    }
}


