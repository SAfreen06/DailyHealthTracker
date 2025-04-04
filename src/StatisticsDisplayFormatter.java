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
        System.out.println("============ 🏆 Health Tracker - Daily Statistics 🏆 ============");

        displayActivityProgress();
        displayWellnessGoalsProgress();
        displayStreaksAndStats();

        System.out.println("==================================================");
    }

    private void displayActivityProgress() {
        System.out.println("\n **Physical & Mental Activity Progress**");
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
        System.out.println("\n📌**Daily Wellness Goals Progress**");
        System.out.println("-------------------------------------------------------------");
        System.out.println("| Wellness Goal     | Goal                  | Achieved     |Progress   | ");
        System.out.println("-------------------------------------------------------------");

        System.out.printf("| 💧 Water Intake   | 8 glasses/day         | %d glasses  |  %d%%  |  \n",
                wellnessGoals.getWaterIntake(), calculatePercentage(wellnessGoals.getWaterIntake(), 8));

        System.out.printf("| 😴 Sleep Duration | 8 hours/night         | %d hours            |  %d%%  |\n",
                wellnessGoals.getSleepDuration(), calculatePercentage(wellnessGoals.getSleepDuration(), 8));

        System.out.printf("| 🌿 Self Care      | 30 min/day            | %d min            |  %d%%  |\n",
                wellnessGoals.getSelfCareTime(), calculatePercentage(wellnessGoals.getSelfCareTime(), 30));

        System.out.printf("| 📱 Screen Time    | 2 hours max/day       | %d hours              | %d%%  | \n",
                wellnessGoals.getScreenTime(), calculatePercentage(wellnessGoals.getScreenTime(), 2));

        System.out.println("-------------------------------------------------------------");
    }

    private void displayStreaksAndStats() {
        System.out.println("\n🔥 **Current Streaks**");
        System.out.println("   🏋️ Exercise: " + streakManager.getCurrentStreak("Exercise") + " Days");
        System.out.println("   🧘 Meditation: " + streakManager.getCurrentStreak("Meditation") + " Days");
        System.out.println("   🍽️ Nutrition: " + streakManager.getCurrentStreak("Nutrition") + " Days");

        System.out.println("\n🏆 **Best Streaks**");
        System.out.println("   🏋️ Exercise: " + streakManager.getBestStreak("Exercise") + " Days");
        System.out.println("   🧘 Meditation: " + streakManager.getBestStreak("Meditation") + " Days");
        System.out.println("   🍽️ Nutrition: " + streakManager.getBestStreak("Nutrition") + " Days");

        // Calculate total stats
        int totalCaloriesBurned = statsCalculator.calculateTotalCaloriesBurned();
        int totalMeditationMinutes = statsCalculator.calculateTotalMeditationMinutes();
        int totalMealsLogged = statsCalculator.calculateTotalMealsLogged();

        System.out.printf("\n📊 **Calories Burned This Week**: %d kcal\n", totalCaloriesBurned);
        System.out.printf("📆 **Total Meditation Minutes**: %d min\n", totalMeditationMinutes);
        System.out.printf("🥗 **Total Meals Logged**: %d\n", totalMealsLogged);
    }

    private int calculatePercentage(int value, int total) {
        return total == 0 ? 0 : (value * 100 / total);
    }
}

