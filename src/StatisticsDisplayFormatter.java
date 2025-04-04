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

    private void displayActivityProgress() {
        System.out.println("\n📌\u001B[33m**Physical & Mental Activity Progress**\u001B[0m");
        System.out.println("-------------------------------------------------------------");
        System.out.println("| Category      | Goal                  | Achieved |  Progress   ");
        System.out.println("-------------------------------------------------------------");

        // Exercise progress
        int exerciseGoal = activityGoals.getExerciseGoal();
        int exerciseProgress = statsCalculator.calculateCategoryProgress("Exercise");
        System.out.printf("| 🏋️ Exercise  | %d min/day            | %d min           | %d%%  | \n",
                exerciseGoal, exerciseProgress, calculatePercentage(exerciseProgress, exerciseGoal));

        // Meditation progress
        int meditationGoal = activityGoals.getMeditationGoal();
        int meditationProgress = statsCalculator.calculateCategoryProgress("Meditation");
        System.out.printf("| 🧘 Meditation | %d min/day            |  %d min            |  %d%%  | \n",
                meditationGoal, meditationProgress, calculatePercentage(meditationProgress, meditationGoal));

        // Nutrition progress
        int nutritionGoal = activityGoals.getNutritionGoal();
        int nutritionProgress = statsCalculator.calculateCategoryProgress("Nutrition");
        System.out.printf("| 🍽️ Nutrition | %d kcal/day         |   %d kcal           |  %d%%  | \n",
                nutritionGoal, nutritionProgress, calculatePercentage(nutritionProgress, nutritionGoal));

        System.out.println("-------------------------------------------------------------");
    }

    private void displayWellnessGoalsProgress() {
        System.out.println("\n📌\u001B[33m**Daily Wellness Goals Progress**\u001B[0m");
        System.out.println("-------------------------------------------------------------");
        System.out.println("| Wellness Goal     | Goal                  | Achieved     |Progress   | ");
        System.out.println("-------------------------------------------------------------");

        System.out.printf("| 💧 Water Intake   | 8 glasses/day         | %d glasses  |  %d%%  |  \n",
                wellnessGoals.getWaterIntake(), calculatePercentage(wellnessGoals.getWaterIntake(), 8));

        System.out.printf("| 😴 Sleep Duration | 8 hours/night         | %d hours            |  %d%%  |\n",
                wellnessGoals.getSleepDuration(), calculatePercentage(wellnessGoals.getSleepDuration(), 8));

        System.out.printf("| 🌿 Self Care      | 30 min/day            | %d min            |  %d%%  |\n",
                wellnessGoals.getSelfCareTime(), calculatePercentage(wellnessGoals.getSelfCareTime(), 30));

        System.out.printf("| 📱 Screen Time    | 2 hours max/day       | %d hours              | %s  | \n",
                wellnessGoals.getScreenTime(), screenTimeCalculation(wellnessGoals.getScreenTime(), 2));

        System.out.println("-------------------------------------------------------------");
    }

    private void displayStreaksAndStats() {
        System.out.println("\n🔥 \u001B[33m**Current Streaks**\u001B[0m");
        System.out.println("   🏋️ Exercise: " + streakManager.getCurrentStreak("Exercise") + " Days");
        System.out.println("   🧘 Meditation: " + streakManager.getCurrentStreak("Meditation") + " Days");
        System.out.println("   🍽️ Nutrition: " + streakManager.getCurrentStreak("Nutrition") + " Days");

        System.out.println("\n🏆 \u001B[33m**Best Streaks**\u001B[0m");
        System.out.println("   🏋️ Exercise: " + streakManager.getBestStreak("Exercise") + " Days");
        System.out.println("   🧘 Meditation: " + streakManager.getBestStreak("Meditation") + " Days");
        System.out.println("   🍽️ Nutrition: " + streakManager.getBestStreak("Nutrition") + " Days");

        // Calculate total stats
        int totalCaloriesBurned = statsCalculator.calculateTotalCaloriesBurned();
        int totalMeditationMinutes = statsCalculator.calculateTotalMeditationMinutes();
        int totalMealsLogged = statsCalculator.calculateTotalMealsLogged();

        System.out.printf("\n📊\u001B[33m**Calories Burned This Week**\u001B[0m: %d kcal\n", totalCaloriesBurned);
        System.out.printf("📆 \u001B[33m**Total Meditation Minutes**\u001B[0m: %d min\n", totalMeditationMinutes);
        System.out.printf("🥗 \u001B[33m**Total Meals Logged**\u001B[0m: %d\n", totalMealsLogged);
    }

    private static int calculatePercentage(int value, int total) {
        if (total == 0) return 0;
        int percentage = value * 100 / total;
        return Math.min(percentage, 100);
    }

    private String screenTimeCalculation(int value, int total) {
        String message;
        if (value > total) {
            message=value-total+" hour over limit";
        }
        else
        {
            message="Within limit";
        }
        return message;
    }
}
