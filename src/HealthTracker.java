import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

class HealthTracker {
    private List<Activity> activities;
    private WellnessGoals wellnessGoals;
    private int exerciseGoal;
    private int meditationGoal;
    private int nutritionGoal;
    private StreakManager streakManager;  // New field for streak management

    public HealthTracker() {
        activities = new ArrayList<>();
        wellnessGoals = new WellnessGoals();
        streakManager = new StreakManager();  // Initialize streak manager

        // Check streaks against current date (handles streak resets if days were missed)
        streakManager.checkAllStreaks();

        // Default goals
        exerciseGoal = 45;
        meditationGoal = 15;
        nutritionGoal = 1800;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void displayActivities() {
        System.out.println("-------------------------------------------------");
        System.out.println("| #  | Activity Name | Category   | Status      |");
        System.out.println("-------------------------------------------------");
        for (int i = 0; i < activities.size(); i++) {
            Activity activity = activities.get(i);
            System.out.printf("| %-2d | %-13s | %-10s | %-10s |\n",
                    i + 1,
                    activity.getName(),
                    activity.getCategory(),
                    activity.isCompleted() ? "Completed" : "Pending");
        }
        System.out.println("-------------------------------------------------");
    }

    public void displayActivitiesWithDetails() {
        System.out.println("-------------------------------------------------");
        System.out.println("| #  | Activity Name | Category   | Description | Status   |");
        System.out.println("-------------------------------------------------");
        for (int i = 0; i < activities.size(); i++) {
            Activity activity = activities.get(i);
            System.out.printf("| %-2d | %-13s | %-10s | %-10s | %-8s |\n",
                    i + 1,
                    activity.getName(),
                    activity.getCategory(),
                    activity.getDescription(),
                    activity.isCompleted() ? "Completed" : "Pending");
        }
        System.out.println("-------------------------------------------------");
    }

    public void completeActivity(int index) {
        Activity activity = activities.get(index);
        boolean wasAlreadyCompleted = activity.isCompleted();

        activity.complete();

        // Only update streak if this is the first time completing this activity today
        if (!wasAlreadyCompleted) {
            // Update streak for the activity's category
            streakManager.recordActivity(activity.getCategory(), LocalDate.now());
        }
    }

    public void deleteActivity(int index) {
        activities.remove(index);
    }

    public void editActivityName(int index, String newName) {
        activities.get(index).setName(newName);
    }

    public void editActivityDescription(int index, String newDescription) {
        activities.get(index).setDescription(newDescription);
    }

    public String getActivityName(int index) {
        return activities.get(index).getName();
    }

    public WellnessGoals getWellnessGoals() {
        return wellnessGoals;
    }

    public void setExerciseGoal(int goal) {
        this.exerciseGoal = goal;
    }

    public void setMeditationGoal(int goal) {
        this.meditationGoal = goal;
    }

    public void setNutritionGoal(int goal) {
        this.nutritionGoal = goal;
    }

    public void displayDailyStatistics() {
        System.out.println("============ 🏆 Health Tracker - Daily Statistics 🏆 ============");
        System.out.println("\n **Physical & Mental Activity Progress**");
        System.out.println("-------------------------------------------------------------");
        System.out.println("| Category      | Goal                  | Achieved |  Progress   ");
        System.out.println("-------------------------------------------------------------");

        // Calculate exercise progress
        int exerciseProgress = calculateCategoryProgress("Exercise", exerciseGoal);
        System.out.printf("| 🏋️ Exercise  | %d min/day            | %d min           | %d%%  | \n",
                exerciseGoal, exerciseProgress, (exerciseProgress * 100 / exerciseGoal));

        // Calculate meditation progress
        int meditationProgress = calculateCategoryProgress("Meditation", meditationGoal);
        System.out.printf("| 🧘 Meditation | %d min/day            |  %d min            | %d%%  | \n",
                meditationGoal, meditationProgress, (meditationProgress * 100 / meditationGoal));

        // Calculate nutrition progress
        int nutritionProgress = calculateCategoryProgress("Nutrition", nutritionGoal);
        System.out.printf("| 🍽️ Nutrition | %d kcal/day         |   %d kcal           |  %d%%  | \n",
                nutritionGoal, nutritionProgress, (nutritionProgress * 100 / nutritionGoal));

        System.out.println("-------------------------------------------------------------");

        // Wellness Goals Section
        displayWellnessGoalsProgress();

        // Streaks and Additional Stats
        displayStreaksAndStats();

        System.out.println("==================================================");
    }

    private void displayWellnessGoalsProgress() {
        System.out.println("\n📌**Daily Wellness Goals Progress**");
        System.out.println("-------------------------------------------------------------");
        System.out.println("| Wellness Goal     | Goal                  | Achieved     |Progress   | ");
        System.out.println("-------------------------------------------------------------");

        System.out.printf("| 💧 Water Intake   | 8 glasses/day         | %d glasses  | %d%%  |  \n",
                wellnessGoals.getWaterIntake(), (wellnessGoals.getWaterIntake() * 100 / 8));

        System.out.printf("| 😴 Sleep Duration | 8 hours/night         | %d hours            | %d%%  |\n",
                wellnessGoals.getSleepDuration(), (wellnessGoals.getSleepDuration() * 100 / 8));

        System.out.printf("| 🌿 Self Care      | 30 min/day            | %d min            | %d%%  |\n",
                wellnessGoals.getSelfCareTime(), (wellnessGoals.getSelfCareTime() * 100 / 30));

        System.out.printf("| 📱 Screen Time    | 2 hours max/day       | %d hours              | %d%%  | \n",
                wellnessGoals.getScreenTime(), (wellnessGoals.getScreenTime() * 100 / 2));

        System.out.println("-------------------------------------------------------------");
    }

    private void displayStreaksAndStats() {
        System.out.println("\n🔥 **Best Streaks**");
        System.out.println("   🏋️ Exercise: " + streakManager.getCurrentStreak("Exercise") + " Days");
        System.out.println("   🧘 Meditation: " + streakManager.getCurrentStreak("Meditation") + " Days");
        System.out.println("   🍽️ Nutrition: " + streakManager.getCurrentStreak("Nutrition") + " Days");

        // Calculate total stats
        int totalCaloriesBurned = calculateTotalCaloriesBurned();
        int totalMeditationMinutes = calculateTotalMeditationMinutes();
        int totalMealsLogged = calculateTotalMealsLogged();

        System.out.printf("\n📊 **Calories Burned This Week**: %d kcal\n", totalCaloriesBurned);
        System.out.printf("📆 **Total Meditation Minutes**: %d min\n", totalMeditationMinutes);
        System.out.printf("🥗 **Total Meals Logged**: %d\n", totalMealsLogged);
    }

    private int calculateCategoryProgress(String category, int goal) {
        return activities.stream()
                .filter(a -> a.getCategory().equals(category) && a.isCompleted())
                .mapToInt(Activity::getProgress)
                .sum();
    }

    private int calculateTotalCaloriesBurned() {
        return activities.stream()
                .filter(a -> a instanceof ExerciseActivity && a.isCompleted())
                .mapToInt(a -> ((ExerciseActivity)a).getCaloriesBurnt())
                .sum();
    }

    private int calculateTotalMeditationMinutes() {
        return activities.stream()
                .filter(a -> a instanceof MeditationActivity && a.isCompleted())
                .mapToInt(a -> ((MeditationActivity)a).getProgress())
                .sum();
    }

    private int calculateTotalMealsLogged() {
        return activities.stream()
                .filter(a -> a instanceof NutritionActivity && a.isCompleted())
                .mapToInt(a -> ((NutritionActivity)a).getMealsLogged())
                .sum();
    }

    // New method to get current streak info for a category
    public int getCurrentStreak(String category) {
        return streakManager.getCurrentStreak(category);
    }

    // New method to get best streak info for a category
    public int getBestStreak(String category) {
        return streakManager.getBestStreak(category);
    }
}
