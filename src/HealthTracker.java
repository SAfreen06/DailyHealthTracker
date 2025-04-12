import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

class HealthTracker {
    private List<Activity> allActivities;
    private List<Activity> todayActivities;
    private WellnessGoals wellnessGoals;
    private ActivityGoals activityGoals;
    private StreakManager streakManager;
    private StatisticsCalculator statsCalculator;
    private IActivityStorage activityStorage;
    private LocalDate currentDay;

    public HealthTracker() {
        this(new CSVActivityStorage());
    }

    public HealthTracker(IActivityStorage activityStorage) {
        this.activityStorage = activityStorage;
        this.currentDay = LocalDate.now();

        this.allActivities = activityStorage.loadAllActivities();


        refreshTodayActivities();

        wellnessGoals = new WellnessGoals();
        streakManager = new StreakManager();
        activityGoals = new ActivityGoals(45, 15, 1800); // Default goals

        // Pass only today's activities to the stats calculator
        statsCalculator = new StatisticsCalculator(todayActivities);

        streakManager.checkAllStreaks();
    }

    // Refresh today's activities list (for date change or after loading)
    private void refreshTodayActivities() {
        LocalDate today = LocalDate.now();
        if (!today.equals(currentDay)) {
            currentDay = today;
        }

        todayActivities = allActivities.stream()
                .filter(activity -> activity.getDate().equals(currentDay))
                .collect(Collectors.toList());


        if (statsCalculator != null) {
            statsCalculator.updateActivities(todayActivities);
        }
    }

    public void addActivity(Activity activity) {
        allActivities.add(activity);


        if (activity.getDate().equals(currentDay)) {
            todayActivities.add(activity);
        }


        saveActivities();
    }


    private void saveActivities() {
        activityStorage.saveActivities(allActivities);
    }

    public void displayActivities() {
        ActivityDisplayFormatter.displayBasicActivities(todayActivities);
    }

    public void displayActivitiesWithDetails() {
        ActivityDisplayFormatter.displayDetailedActivities(todayActivities);
    }

    public boolean completeActivity(int index) {
        if (!isValidActivityIndex(index)) {
            return false;
        }

        Activity activity = todayActivities.get(index);
        boolean wasAlreadyCompleted = activity.isCompleted();

        activity.complete();

        if (!wasAlreadyCompleted) {
            streakManager.recordActivity(activity.getCategory());
        }


        saveActivities();
        return true;
    }

    public boolean deleteActivity(int index) {
        if (isValidActivityIndex(index)) {
            Activity activityToRemove = todayActivities.get(index);
            todayActivities.remove(index);
            allActivities.remove(activityToRemove);


            saveActivities();
            return true;
        }
        return false;
    }

    public boolean editActivityName(int index, String newName) {
        if (isValidActivityIndex(index)) {
            todayActivities.get(index).setName(newName);
            saveActivities();
            return true;
        }
        return false;
    }

    public boolean editActivityDescription(int index, String newDescription) {
        if (isValidActivityIndex(index)) {
            todayActivities.get(index).setDescription(newDescription);
            saveActivities();
            return true;
        }
        return false;
    }

    public String getActivityName(int index) {
        if (isValidActivityIndex(index)) {
            return todayActivities.get(index).getName();
        }
        return "";
    }

    public boolean isValidActivityIndex(int index) {
        return index >= 0 && index < todayActivities.size();
    }

    public int getActivityCount() {
        return todayActivities.size();
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
        refreshTodayActivities();

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

    public void checkForDateChange() {
        LocalDate today = LocalDate.now();
        if (!today.equals(currentDay)) {
            refreshTodayActivities();
        }
    }
}