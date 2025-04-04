import java.util.Scanner;

public class HealthTrackerApp {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";

    private static Scanner scanner = new Scanner(System.in);
    private static HealthTracker healthTracker = new HealthTracker();

    public static void main(String[] args) {
        clearScreen();
        welcomeBanner();

        while (true) {
            displayMainMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        clearScreen();
                        addNewActivity();
                        break;
                    case 2:
                        clearScreen();
                        logActivityProgress();
                        break;
                    case 3:
                        clearScreen();
                        editOrDeleteActivities();
                        break;
                    case 4:
                        clearScreen();
                        setDailyHealthGoals();
                        break;
                    case 5:
                        clearScreen();
                        viewProgressAndStatistics();
                        break;
                    case 6:
                        exitBanner();
                        return;
                    default:
                        showErrorMessage("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                showErrorMessage("Please enter a valid number.");
            }
        }
    }

    private static void welcomeBanner() {
        System.out.println(ANSI_BLUE +
                "╔══════════════════════════════════════╗\n" +
                "║      🌟 WELCOME TO HEALTH TRACKER 🌟  ║\n" +
                "║    Your Personal Wellness Companion   ║\n" +
                "╚══════════════════════════════════════╝" + ANSI_RESET);
        pause(1000);
    }

    private static void exitBanner() {
        System.out.println(ANSI_GREEN +
                "╔══════════════════════════════════════╗\n" +
                "║     🌈 Stay Healthy, Stay Happy! 🌈   ║\n" +
                "║       Your Wellness Journey Rocks!    ║\n" +
                "╚══════════════════════════════════════╝" + ANSI_RESET);
    }

    private static void displayMainMenu() {
        System.out.println(ANSI_PURPLE + "❖ DAILY HEALTH TRACKER MENU ❖" + ANSI_RESET);
        System.out.println(ANSI_BLUE +
                "1. 💪 Add a New Activity\n" +
                "2. 📝 Log Activity Progress\n" +
                "3. ✏️ Edit or Delete Activities\n" +
                "4. 🎯 Set Daily Health Goals\n" +
                "5. 📊 View Progress & Statistics\n" +
                "6. 🚪 Exit" + ANSI_RESET);
        System.out.print(ANSI_YELLOW + "➤ Choose an option: " + ANSI_RESET);
    }

    private static void addNewActivity() {
        System.out.println(ANSI_GREEN + "❖ Choose Health Category ❖" + ANSI_RESET);
        String[] categories = {"Exercise", "Meditation", "Nutrition"};

        for (int i = 0; i < categories.length; i++) {
            System.out.println((i+1) + ". " + categories[i]);
        }

        try {
            System.out.print(ANSI_YELLOW + "➤ Select Category: " + ANSI_RESET);
            int categoryChoice = Integer.parseInt(scanner.nextLine());

            System.out.print("Activity Name: ");
            String name = scanner.nextLine();

            System.out.print("Description: ");
            String description = scanner.nextLine();

            Activity activity = createActivityBasedOnCategory(categoryChoice, name, description);

            if (activity != null) {
                healthTracker.addActivity(activity);
                showSuccessMessage(name + " added successfully for today!");
            }
        } catch (NumberFormatException e) {
            showErrorMessage("Invalid input. Please enter a number.");
        }
        pressEnterToContinue();
    }

    private static void logActivityProgress() {
        while (true) {
            System.out.println(ANSI_CYAN + "--- Log Your Activity Progress ---" + ANSI_RESET);
            System.out.println("1. 📝 Log Activities");
            System.out.println("2. 🌈 Log Daily Wellness Goals");
            System.out.println("3. 🔙 Return");
            System.out.print(ANSI_YELLOW + "➤ Enter Your Choice: " + ANSI_RESET);

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
                        showErrorMessage("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                showErrorMessage("Please enter a valid number.");
            }
        }
    }

    private static void logActivities() {
        if (healthTracker.getActivityCount() == 0) {
            showErrorMessage("No activities to log. Please create an activity first.");
            return;
        }

        healthTracker.displayActivities();

        try {
            System.out.print(ANSI_YELLOW + "Choose an activity to log progress (Enter #): "+ANSI_RESET);
            int activityIndex = Integer.parseInt(scanner.nextLine()) - 1;

            if (!healthTracker.isValidActivityIndex(activityIndex)) {
                showErrorMessage("Invalid activity index.");
                return;
            }

            System.out.print(ANSI_YELLOW+"Have you completed this activity? (Y/N) "+ANSI_RESET);
            String completed = scanner.nextLine();

            if (completed.equalsIgnoreCase("Y")) {
                if (healthTracker.completeActivity(activityIndex)) {
                    showSuccessMessage("Activity marked as Completed!");
                } else {
                    showErrorMessage("Could not complete the activity.");
                }
            }
        } catch (NumberFormatException e) {
            showErrorMessage("Invalid input. Please enter a number.");
        }
        pressEnterToContinue();
    }

    private static void logDailyWellnessGoals() {
        WellnessGoals goals = healthTracker.getWellnessGoals();
        goals.displayGoals();

        while (true) {
            try {
                System.out.print("Choose an activity to log progress (Enter #, or 0 to finish): ");
                int goalIndex = Integer.parseInt(scanner.nextLine());

                if (goalIndex == 0) break;

                switch (goalIndex) {
                    case 1:
                        System.out.print("Enter the number of glasses of water you drank today: ");
                        goals.setWaterIntake(Integer.parseInt(scanner.nextLine()));
                        break;
                    case 2:
                        System.out.print("Enter the number of hours you slept today: ");
                        goals.setSleepDuration(Integer.parseInt(scanner.nextLine()));
                        break;
                    case 3:
                        System.out.print("Enter the number of minutes of self-care today: ");
                        goals.setSelfCareTime(Integer.parseInt(scanner.nextLine()));
                        break;
                    case 4:
                        System.out.print("Enter the time spent on screens today (hours): ");
                        goals.setScreenTime(Integer.parseInt(scanner.nextLine()));
                        break;
                    default:
                        showErrorMessage("Invalid goal.");
                }
            } catch (NumberFormatException e) {
                showErrorMessage("Please enter a valid number.");
            }
        }
        showSuccessMessage("Daily wellness goals updated!");
        pressEnterToContinue();
    }

    private static void editOrDeleteActivities() {
        if (healthTracker.getActivityCount() == 0) {
            showErrorMessage("No activities available to edit or delete.");
            pressEnterToContinue();
            return;
        }

        healthTracker.displayActivitiesWithDetails();

        try {
            System.out.print("Enter the activity number to edit/delete: ");
            int activityIndex = Integer.parseInt(scanner.nextLine()) - 1;

            if (!healthTracker.isValidActivityIndex(activityIndex)) {
                showErrorMessage("Invalid activity index.");
                pressEnterToContinue();
                return;
            }

            System.out.println(ANSI_YELLOW + "Options for " + healthTracker.getActivityName(activityIndex) + ":" + ANSI_RESET);
            System.out.println("1. ✏️ Edit Name");
            System.out.println("2. 📝 Edit Description");
            System.out.println("3. 🗑️ Delete Activity");
            System.out.println("4. 🚫 Cancel");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    if (healthTracker.editActivityName(activityIndex, newName)) {
                        showSuccessMessage("Activity name updated!");
                    } else {
                        showErrorMessage("Failed to update activity name.");
                    }
                    break;
                case 2:
                    System.out.print("Enter new description: ");
                    String newDescription = scanner.nextLine();
                    if (healthTracker.editActivityDescription(activityIndex, newDescription)) {
                        showSuccessMessage("Activity description updated!");
                    } else {
                        showErrorMessage("Failed to update activity description.");
                    }
                    break;
                case 3:
                    System.out.print("Are you sure you want to delete \"" +
                            healthTracker.getActivityName(activityIndex) + "\"? (Y/N) ");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        if (healthTracker.deleteActivity(activityIndex)) {
                            showSuccessMessage("Activity deleted successfully.");
                        } else {
                            showErrorMessage("Failed to delete activity.");
                        }
                    }
                    break;
                case 4:
                    return;
                default:
                    showErrorMessage("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            showErrorMessage("Invalid input. Please enter a number.");
        }
        pressEnterToContinue();
    }

    private static void setDailyHealthGoals() {
        while (true) {
            System.out.println(ANSI_PURPLE + "--- Set Your Daily Health Goals ---" + ANSI_RESET);
            System.out.println("1. 💪 Set Exercise Goal (Minutes per Day)");
            System.out.println("2. 🧘 Set Meditation Goal (Minutes per Day)");
            System.out.println("3. 🍎 Set Nutrition Goal (Calories per Day)");
            System.out.println("4. 🔙 Return");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        System.out.print("Enter your new daily exercise goal (minutes): ");
                        int exerciseGoal = Integer.parseInt(scanner.nextLine());
                        healthTracker.setExerciseGoal(exerciseGoal);
                        showSuccessMessage("Daily exercise goal updated to " + exerciseGoal + " minutes per day.");
                        break;
                    case 2:
                        System.out.print("Enter your new daily meditation goal (minutes): ");
                        int meditationGoal = Integer.parseInt(scanner.nextLine());
                        healthTracker.setMeditationGoal(meditationGoal);
                        showSuccessMessage("Daily meditation goal updated to " + meditationGoal + " minutes per day.");
                        break;
                    case 3:
                        System.out.print("Enter your new daily calorie intake goal: ");
                        int calorieGoal = Integer.parseInt(scanner.nextLine());
                        healthTracker.setNutritionGoal(calorieGoal);
                        showSuccessMessage("Daily nutrition goal updated to " + calorieGoal + " kcal per day.");
                        break;
                    case 4:
                        return;
                    default:
                        showErrorMessage("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                showErrorMessage("Please enter a valid number.");
            }
            pressEnterToContinue();
        }
    }

    private static void viewProgressAndStatistics() {
        healthTracker.displayDailyStatistics();
        pressEnterToContinue();
    }

    private static void pressEnterToContinue() {
        System.out.println(ANSI_GREEN + "(Press Enter to return to the menu...)" + ANSI_RESET);
        scanner.nextLine(); // Wait for user to press Enter
    }

    private static void showSuccessMessage(String message) {
        System.out.println(ANSI_GREEN + "✅ " + message + ANSI_RESET);
        pause(1000);
    }

    private static void showErrorMessage(String message) {
        System.out.println(ANSI_RED + "❌ " + message + ANSI_RESET);
        pause(1000);
    }

    private static void clearScreen() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (Exception e) {
            // If clearing screen fails, print multiple newlines
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }

    private static void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static Activity createActivityBasedOnCategory(int categoryChoice, String name, String description) {
        try {
            switch (categoryChoice) {
                case 1:
                    System.out.print("Calories Burnt per Session: ");
                    int caloriesBurnt = Integer.parseInt(scanner.nextLine());

                    System.out.print("Exercise Duration (minutes): ");
                    int exerciseDuration = Integer.parseInt(scanner.nextLine());

                    return new ExerciseActivity(name, description, caloriesBurnt, exerciseDuration);
                case 2:
                    System.out.print("Meditation Duration (minutes): ");
                    int meditationDuration = Integer.parseInt(scanner.nextLine());

                    return new MeditationActivity(name, description, meditationDuration);
                case 3:
                    System.out.print("Calories Consumed: ");
                    int caloriesConsumed = Integer.parseInt(scanner.nextLine());

                    System.out.print("Number of Meals Logged: ");
                    int mealsLogged = Integer.parseInt(scanner.nextLine());

                    return new NutritionActivity(name, description, caloriesConsumed, mealsLogged);
                default:
                    showErrorMessage("Invalid category.");
                    return null;
            }
        } catch (NumberFormatException e) {
            showErrorMessage("Invalid numeric input.");
            return null;
        }
    }
}