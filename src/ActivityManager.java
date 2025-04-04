import java.util.Scanner;

class ActivityManager {
    private final Scanner scanner;
    private final HealthTracker healthTracker;
    private final UIManager uiManager;

    public ActivityManager(Scanner scanner, HealthTracker healthTracker, UIManager uiManager) {
        this.scanner = scanner;
        this.healthTracker = healthTracker;
        this.uiManager = uiManager;
    }

    public void addNewActivity() {
        uiManager.displayPrompt("❖Choose Health Category❖\n");
        String[] categories = {"Exercise", "Meditation", "Nutrition"};

        for (int i = 0; i < categories.length; i++) {
            uiManager.displayText((i+1) + ". " + categories[i]);
        }

        try {
            uiManager.displayPrompt("➤ Select Category: ");
            int categoryChoice = Integer.parseInt(scanner.nextLine());

            uiManager.displayPrompt("Activity Name: ");
            String name = scanner.nextLine();

            uiManager.displayPrompt("Description: ");
            String description = scanner.nextLine();

            Activity activity = createActivityBasedOnCategory(categoryChoice, name, description);

            if (activity != null) {
                healthTracker.addActivity(activity);
                uiManager.showSuccessMessage(name + " added successfully for today!");
            }
        } catch (NumberFormatException e) {
            uiManager.showErrorMessage("Invalid input. Please enter a number.");
        }
        waitForUserToContinue();
    }

    public void logActivityProgress() {
        while (true) {
            uiManager.displayPrompt("--- Log Your Activity Progress ---\n");
            uiManager.displayText("1. 📝 Log Activities");
            uiManager.displayText("2. 🌈 Log Daily Wellness Goals");
            uiManager.displayText("3. 🔙 Return");
            uiManager.displayPrompt("➤ Enter Your Choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        logActivities();
                        break;
                    case 2:
                        logDailyWellnessGoals();
                        break;
                    case 3:
                        return;
                    default:
                        uiManager.showErrorMessage("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                uiManager.showErrorMessage("Please enter a valid number.");
            }
        }
    }

    private void logActivities() {
        if (healthTracker.getActivityCount() == 0) {
            uiManager.showErrorMessage("No activities to log. Please create an activity first.");
            return;
        }

        healthTracker.displayActivities();

        try {
            uiManager.displayPrompt("Choose an activity to log progress (Enter #): ");
            int activityIndex = Integer.parseInt(scanner.nextLine()) - 1;

            if (!healthTracker.isValidActivityIndex(activityIndex)) {
                uiManager.showErrorMessage("Invalid activity index.");
                return;
            }

            uiManager.displayPrompt("Have you completed this activity? (Y/N) :");
            String completed = scanner.nextLine();

            if (completed.equalsIgnoreCase("Y")) {
                if (healthTracker.completeActivity(activityIndex)) {
                    uiManager.showSuccessMessage("Activity marked as Completed!");
                } else {
                    uiManager.showErrorMessage("Could not complete the activity.");
                }
            }
        } catch (NumberFormatException e) {
            uiManager.showErrorMessage("Invalid input. Please enter a number.");
        }
        waitForUserToContinue();
    }

    private void logDailyWellnessGoals() {
        WellnessGoals goals = healthTracker.getWellnessGoals();
        uiManager.displayPrompt("--- 🌈 Log Your Daily Wellness Goals ---\n");


        String[] goalNames = {"Water Intake", "Sleep Duration", "Self-Care Time", "Screen Time"};
        String[] goalIcons = {"💧", "😴", "🌿", "📱"};
        String[] goalUnits = {"glasses", "hours", "minutes", "hours"};
        String[] goalTips = {
                "\u001B[34mHealth tip: Aim for 8 glasses (2L) of water daily for proper hydration\u001B[0m",
                "\u001B[34mHealth tip: 7-9 hours is recommended for adults for optimal rest\u001B[0m",
                "\u001B[34mHealth tip: Even 10-15 minutes of self-care daily can reduce stress\u001B[0m",
                "\u001B[34mHealth tip: Try to limit recreational screen time to 2 hours per day\u001B[0m"
        };
        int[] recommendedValues = {8, 8, 30, 2};
        int[] minValues = {0, 0, 0, 0};
        int[] maxValues = {20, 24, 180, 24};

        boolean continueLogging = true;

        while (continueLogging) {
            uiManager.displayPrompt("\nSelect a wellness goal to update:\n");
            for (int i = 0; i < goalNames.length; i++) {
                uiManager.displayText((i+1) + ". " + goalIcons[i] + " " + goalNames[i] +
                        " (Current: " + getCurrentValue(goals, i) + " " + goalUnits[i] + ")");
            }
            uiManager.displayText("0. ✅ Save and Return");

            try {
                uiManager.displayPrompt("➤ Your choice: ");
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 0) {
                    continueLogging = false;
                    continue;
                }

                if (choice < 1 || choice > goalNames.length) {
                    uiManager.showErrorMessage("Invalid choice. Please try again.");
                    waitForUserToContinue();
                    continue;
                }

                int index = choice - 1;
                uiManager.displayText(goalTips[index]);
                uiManager.displayPrompt("Enter " + goalNames[index] + " (" + goalUnits[index] +
                        ", recommended: " + recommendedValues[index] + "): ");


                String input = scanner.nextLine();

                // Check if input is empty (user just pressed enter)
                if (input.trim().isEmpty()) {
                    uiManager.displayText("No change made.");
                    continue;
                }

                try {
                    int value = Integer.parseInt(input);

                    // Validate input range
                    if (value < minValues[index] || value > maxValues[index]) {
                        uiManager.showErrorMessage("Value must be between " + minValues[index] +
                                " and " + maxValues[index] + ". Please try again.");
                        continue;
                    }

                    // Update the appropriate goal
                    updateWellnessGoal(goals, index, value);

                    // Show feedback based on the value compared to recommended
                    if (index == 3) { // Screen time (lower is better)
                        if (value <= recommendedValues[index]) {
                            uiManager.showSuccessMessage("Great job keeping screen time low!");
                        } else if (value <= recommendedValues[index] * 1.5) {
                            uiManager.displayText("Try to reduce screen time when possible.");
                        } else {
                            uiManager.displayText("Consider setting limits on screen usage.");
                        }
                    } else { // For other metrics (higher is better)
                        if (value >= recommendedValues[index]) {
                            uiManager.showSuccessMessage("Excellent progress on your " + goalNames[index] + "!");
                        } else if (value >= recommendedValues[index] * 0.7) {
                            uiManager.displayText("Good effort! Keep working toward your goal.");
                        } else {
                            uiManager.displayText("Try to increase this value tomorrow.");
                        }
                    }
                } catch (NumberFormatException e) {
                    uiManager.showErrorMessage("Please enter a valid number.");
                }
            } catch (NumberFormatException e) {
                uiManager.showErrorMessage("Please enter a valid number.");
            }
        }

        uiManager.showSuccessMessage("Wellness goals updated successfully!");
    }

    // Helper method to get current wellness goal value
    private int getCurrentValue(WellnessGoals goals, int index) {
        switch (index) {
            case 0: return goals.getWaterIntake();
            case 1: return goals.getSleepDuration();
            case 2: return goals.getSelfCareTime();
            case 3: return goals.getScreenTime();
            default: return 0;
        }
    }

    // Helper method to update wellness goal
    private void updateWellnessGoal(WellnessGoals goals, int index, int value) {
        switch (index) {
            case 0: goals.setWaterIntake(value); break;
            case 1: goals.setSleepDuration(value); break;
            case 2: goals.setSelfCareTime(value); break;
            case 3: goals.setScreenTime(value); break;
        }
    }

    public void editOrDeleteActivities() {
        if (healthTracker.getActivityCount() == 0) {
            uiManager.showErrorMessage("No activities available to edit or delete.");
            waitForUserToContinue();
            return;
        }

        healthTracker.displayActivitiesWithDetails();

        try {
            uiManager.displayPrompt("Enter the activity number to edit/delete: ");
            int activityIndex = Integer.parseInt(scanner.nextLine()) - 1;

            if (!healthTracker.isValidActivityIndex(activityIndex)) {
                uiManager.showErrorMessage("Invalid activity index.");
                waitForUserToContinue();
                return;
            }

            uiManager.displayPrompt("Options for " + healthTracker.getActivityName(activityIndex) + ":\n");
            uiManager.displayText("1. ✏️ Edit Name");
            uiManager.displayText("2. 📝 Edit Description");
            uiManager.displayText("3. 🗑️ Delete Activity");
            uiManager.displayText("4. 🚫 Cancel");
            uiManager.displayPrompt("➤ Enter Your Choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    uiManager.displayPrompt("Enter new name: ");
                    String newName = scanner.nextLine();
                    if (healthTracker.editActivityName(activityIndex, newName)) {
                        uiManager.showSuccessMessage("Activity name updated!");
                    } else {
                        uiManager.showErrorMessage("Failed to update activity name.");
                    }
                    break;
                case 2:
                    uiManager.displayPrompt("Enter new description: ");
                    String newDescription = scanner.nextLine();
                    if (healthTracker.editActivityDescription(activityIndex, newDescription)) {
                        uiManager.showSuccessMessage("Activity description updated!");
                    } else {
                        uiManager.showErrorMessage("Failed to update activity description.");
                    }
                    break;
                case 3:
                    uiManager.displayPrompt("Are you sure you want to delete \"" +
                            healthTracker.getActivityName(activityIndex) + "\"? (Y/N) ");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        if (healthTracker.deleteActivity(activityIndex)) {
                            uiManager.showSuccessMessage("Activity deleted successfully.");
                        } else {
                            uiManager.showErrorMessage("Failed to delete activity.");
                        }
                    }
                    break;
                case 4:
                    return;
                default:
                    uiManager.showErrorMessage("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            uiManager.showErrorMessage("Invalid input. Please enter a number.");
        }
        waitForUserToContinue();
    }

    private Activity createActivityBasedOnCategory(int categoryChoice, String name, String description) {
        try {
            String category;
            switch (categoryChoice) {
                case 1: // Exercise
                    uiManager.displayPrompt("Calories Burnt per Session: ");
                    int caloriesBurnt = Integer.parseInt(scanner.nextLine());

                    uiManager.displayPrompt("Exercise Duration (minutes): ");
                    int exerciseDuration = Integer.parseInt(scanner.nextLine());

                    return ActivityFactory.createExerciseActivity(name, description, caloriesBurnt, exerciseDuration);

                case 2: // Meditation
                    uiManager.displayPrompt("Meditation Duration (minutes): ");
                    int meditationDuration = Integer.parseInt(scanner.nextLine());

                    return ActivityFactory.createMeditationActivity(name, description, meditationDuration);

                case 3: // Nutrition
                    uiManager.displayPrompt("Calories Consumed: ");
                    int caloriesConsumed = Integer.parseInt(scanner.nextLine());

                    uiManager.displayPrompt("Number of Meals Logged: ");
                    int mealsLogged = Integer.parseInt(scanner.nextLine());

                    return ActivityFactory.createNutritionActivity(name, description, caloriesConsumed, mealsLogged);

                default:
                    uiManager.showErrorMessage("Invalid category.");
                    return null;
            }
        } catch (NumberFormatException e) {
            uiManager.showErrorMessage("Invalid numeric input.");
            return null;
        }
    }

    private void waitForUserToContinue() {
        uiManager.promptEnterToContinue();
        scanner.nextLine();
    }
}