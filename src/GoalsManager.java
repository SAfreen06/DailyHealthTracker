import java.util.Scanner;

class GoalsManager {
    private final Scanner scanner;
    private final HealthTracker healthTracker;
    private final UIManager uiManager;

    public GoalsManager(Scanner scanner, HealthTracker healthTracker, UIManager uiManager) {
        this.scanner = scanner;
        this.healthTracker = healthTracker;
        this.uiManager = uiManager;
    }

    public void setDailyHealthGoals() {
        while (true) {
            uiManager.displayPrompt("--- Set Your Daily Health Goals ---\n");
            uiManager.displayText("1. 💪 Set Exercise Goal (Minutes per Day)");
            uiManager.displayText("2. 🧘 Set Meditation Goal (Minutes per Day)");
            uiManager.displayText("3. 🍎 Set Nutrition Goal (Calories per Day)");
            uiManager.displayText("4. 🔙 Return");
            uiManager.displayPrompt("➤ Enter Your Choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        uiManager.displayPrompt("Enter your new daily exercise goal (minutes): ");
                        int exerciseGoal = Integer.parseInt(scanner.nextLine());
                        healthTracker.setExerciseGoal(exerciseGoal);
                        uiManager.showSuccessMessage("Daily exercise goal updated to " + exerciseGoal + " minutes per day.");
                        break;
                    case 2:
                        uiManager.displayPrompt("Enter your new daily meditation goal (minutes): ");
                        int meditationGoal = Integer.parseInt(scanner.nextLine());
                        healthTracker.setMeditationGoal(meditationGoal);
                        uiManager.showSuccessMessage("Daily meditation goal updated to " + meditationGoal + " minutes per day.");
                        break;
                    case 3:
                        uiManager.displayPrompt("Enter your new daily calorie intake goal: ");
                        int calorieGoal = Integer.parseInt(scanner.nextLine());
                        healthTracker.setNutritionGoal(calorieGoal);
                        uiManager.showSuccessMessage("Daily nutrition goal updated to " + calorieGoal + " kcal per day.");
                        break;
                    case 4:
                        return;
                    default:
                        uiManager.showErrorMessage("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                uiManager.showErrorMessage("Please enter a valid number.");
            }
            waitForUserToContinue();
        }
    }

    private void waitForUserToContinue() {
        uiManager.promptEnterToContinue();
        scanner.nextLine();
    }
}

