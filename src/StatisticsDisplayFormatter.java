import java.util.List;

class StatisticsDisplayFormatter {
    private WellnessGoals wellnessGoals;
    private ActivityGoals activityGoals;
    private StreakManager streakManager;
    private StatisticsCalculator statsCalculator;

    public StatisticsDisplayFormatter(
            WellnessGoals wellnessGoals,
            ActivityGoals activityGoals,
            StreakManager streakManager,
            StatisticsCalculator statsCalculator) {
        this.wellnessGoals = wellnessGoals;
        this.activityGoals = activityGoals;
        this.streakManager = streakManager;
        this.statsCalculator = statsCalculator;
    }

    public void displayCompleteStatistics() {
        System.out.println("\u001B[33m============ 🏆 Health Tracker - Daily Statistics 🏆 ============\u001B[0m");

        displayActivityProgress();
        displayWellnessGoalsProgress();
        displayStreaksAndStats();

        System.out.println("\u001B[33m==================================================\u001B[0m");
    }

    public void displayActivityProgress() {
        System.out.println("\n📌\u001B[33m**Physical & Mental Activity Progress**\u001B[0m");
        System.out.println("+-----------------+----------------------+----------------+------------+");
        System.out.println("| Category        | Goal                 | Achieved       | Progress   |");
        System.out.println("+-----------------+----------------------+----------------+------------+");

        // Exercise progress
        int exerciseGoal = activityGoals.getExerciseGoal();
        int exerciseProgress = statsCalculator.calculateCategoryProgress("Exercise");
        System.out.printf("| 🏋️ Exercise     | %d min/day            | %d min         | %3d%%       |\n",
                exerciseGoal, exerciseProgress, calculatePercentage(exerciseProgress, exerciseGoal));

        // Meditation progress
        int meditationGoal = activityGoals.getMeditationGoal();
        int meditationProgress = statsCalculator.calculateCategoryProgress("Meditation");
        System.out.printf("| 🧘 Meditation    | %d min/day            | %d min         | %3d%%       |\n",
                meditationGoal, meditationProgress, calculatePercentage(meditationProgress, meditationGoal));

        // Nutrition progress
        int nutritionGoal = activityGoals.getNutritionGoal();
        int nutritionProgress = statsCalculator.calculateCategoryProgress("Nutrition");
        System.out.printf("| 🍽️ Nutrition     | %d kcal/day          | %d kcal        | %3d%%       |\n",
                nutritionGoal, nutritionProgress, calculatePercentage(nutritionProgress, nutritionGoal));

        System.out.println("+-----------------+----------------------+----------------+------------+");
    }

    public void displayWellnessGoalsProgress() {
        System.out.println("\n📌\u001B[33m**Daily Wellness Goals Progress**\u001B[0m");
        System.out.println("+-----------------+----------------------+----------------+------------+");
        System.out.println("| Wellness Goal   | Goal                 | Achieved       | Progress   |");
        System.out.println("+-----------------+----------------------+----------------+------------+");

        System.out.printf("| 💧 Water Intake  | %d glasses/day        | %d glasses     | %3d%%       |\n",
                8, wellnessGoals.getWaterIntake(), calculatePercentage(wellnessGoals.getWaterIntake(), 8));

        System.out.printf("| 😴 Sleep Duration| %d hours/night        | %d hours       | %3d%%       |\n",
                8, wellnessGoals.getSleepDuration(), calculatePercentage(wellnessGoals.getSleepDuration(), 8));

        System.out.printf("| 🌿 Self Care     | %d min/day           | %d min         | %3d%%       |\n",
                30, wellnessGoals.getSelfCareTime(), calculatePercentage(wellnessGoals.getSelfCareTime(), 30));

        System.out.printf("| 📱 Screen Time   | %d hours max/day      | %d hours       | %-12s |\n",
                2, wellnessGoals.getScreenTime(), screenTimeCalculation(wellnessGoals.getScreenTime(), 2));

        System.out.println("+-----------------+----------------------+----------------+------------+");
    }

    public void displayStreaksAndStats() {
        displayStreaks();
        displayDailyStats();
    }

    private void displayStreaks() {
        System.out.println("\n🔥 \u001B[33m**Current Streaks**\u001B[0m");
        System.out.printf("🏋️ Exercise: %d days\n", streakManager.getCurrentStreak("Exercise"));
        System.out.printf("🧘 Meditation: %d days\n", streakManager.getCurrentStreak("Meditation"));
        System.out.printf("🍽️ Nutrition: %d days\n", streakManager.getCurrentStreak("Nutrition"));

        System.out.println("\n🏆 \u001B[33m**Best Streaks**\u001B[0m");
        System.out.printf("🏋️ Exercise: %d days\n", streakManager.getBestStreak("Exercise"));
        System.out.printf("🧘 Meditation: %d days\n", streakManager.getBestStreak("Meditation"));
        System.out.printf("🍽️ Nutrition: %d days\n", streakManager.getBestStreak("Nutrition"));
    }

    private void displayDailyStats() {
        // Calculate total stats
        int totalCaloriesBurned = statsCalculator.calculateTotalCaloriesBurned();
        int totalMeditationMinutes = statsCalculator.calculateTotalMeditationMinutes();
        int totalMealsLogged = statsCalculator.calculateTotalMealsLogged();

        System.out.println("\n📊 \u001B[33m**Daily Summary**\u001B[0m");
        System.out.printf("🔥 Calories: %d\n", totalCaloriesBurned);
        System.out.printf("🧘 Meditation: %d minutes\n", totalMeditationMinutes);
        System.out.printf("🍽️ Meals Logged: %d\n", totalMealsLogged);
    }

    private static int calculatePercentage(int value, int total) {
        if (total == 0) return 0;
        int percentage = value * 100 / total;
        return Math.min(percentage, 100);
    }

    private String screenTimeCalculation(int value, int total) {
        if (value > total) {
            return (value - total) + "h over limit";
        }
        else {
            return "Within limit";
        }
    }
}
