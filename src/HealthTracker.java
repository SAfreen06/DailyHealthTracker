import java.util.List;
import java.util.ArrayList;
import java.util.Map;


class HealthTracker {
    private List<Activity> activities;
    private WellnessGoals wellnessGoals;
    private ActivityGoals activityGoals;
    private StreakManager streakManager;
    private StatisticsCalculator statsCalculator;

    public HealthTracker() {
        activities = new ArrayList<>();
        wellnessGoals = new WellnessGoals();
        streakManager = new StreakManager();
        activityGoals = new ActivityGoals(45, 15, 1800); // Default goals
        statsCalculator = new StatisticsCalculator(activities);

        streakManager.checkAllStreaks();
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void displayActivities() {
        ActivityDisplayFormatter.displayBasicActivities(activities);
    }

    public void displayActivitiesWithDetails() {
        ActivityDisplayFormatter.displayDetailedActivities(activities);
    }

    public void completeActivity(int index) {
        if (!isValidActivityIndex(index)) {
            System.out.println("Invalid activity index.");
            return;
        }

        Activity activity = activities.get(index);
        boolean wasAlreadyCompleted = activity.isCompleted();

        activity.complete();

        if (!wasAlreadyCompleted) {
            streakManager.recordActivity(activity.getCategory());
        }
    }

    public void deleteActivity(int index) {
        if (isValidActivityIndex(index)) {
            activities.remove(index);
        } else {
            System.out.println("Invalid activity index.");
        }
    }

    public void editActivityName(int index, String newName) {
        if (isValidActivityIndex(index)) {
            activities.get(index).setName(newName);
        } else {
            System.out.println("Invalid activity index.");
        }
    }

    public void editActivityDescription(int index, String newDescription) {
        if (isValidActivityIndex(index)) {
            activities.get(index).setDescription(newDescription);
        } else {
            System.out.println("Invalid activity index.");
        }
    }

    public String getActivityName(int index) {
        if (isValidActivityIndex(index)) {
            return activities.get(index).getName();
        }
        return "";
    }

    private boolean isValidActivityIndex(int index) {
        return index >= 0 && index < activities.size();
    }

    public WellnessGoals getWellnessGoals() {
        return wellnessGoals;
    }

    public void setExerciseGoal(int goal) {
        activityGoals.setExerciseGoal(goal);
    }

    public void setMeditationGoal(int goal) {
        activityGoals.setMeditationGoal(goal);
    }

    public void setNutritionGoal(int goal) {
        activityGoals.setNutritionGoal(goal);
    }

    public void displayDailyStatistics() {
        StatisticsDisplayFormatter formatter = new StatisticsDisplayFormatter(
                wellnessGoals,
                activityGoals,
                streakManager,
                statsCalculator
        );
        formatter.displayCompleteStatistics();
    }

 
    public void addStreakCategory(String category) {
        streakManager.addCategory(category);
    }

    public boolean removeStreakCategory(String category) {
        return streakManager.removeCategory(category);
    }

    public Map<String, Streak> getAllStreakCategories() {
        return streakManager.getAllStreaks();
    }

    public int getCurrentStreak(String category) {
        return streakManager.getCurrentStreak(category);
    }

    public int getBestStreak(String category) {
        return streakManager.getBestStreak(category);
    }
}