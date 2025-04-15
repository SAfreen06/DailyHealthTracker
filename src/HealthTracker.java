import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

class HealthTracker {
    private List<Activity> allActivities;
    private List<Activity> todayActivities;
    private WellnessGoals wellnessGoals;
    private ActivityGoals activityGoals;
    private StreakManager streakManager;
    private StatisticsCalculator statsCalculator;
    private IActivityStorage activityStorage;
    private IWellnessGoalsStorage wellnessGoalsStorage;
    private LocalDate currentDay;

    public HealthTracker() {
        this(new CSVActivityStorage(), new CSVWellnessGoalsStorage());
    }

    public HealthTracker(IActivityStorage activityStorage) {
        this(activityStorage, new CSVWellnessGoalsStorage());
    }

    public HealthTracker(IActivityStorage activityStorage, IWellnessGoalsStorage wellnessGoalsStorage) {
        this.activityStorage = activityStorage;
        this.wellnessGoalsStorage = wellnessGoalsStorage;
        this.currentDay = LocalDate.now();

        this.allActivities = activityStorage.loadAllActivities();

        refreshTodayActivities();


        this.wellnessGoals = wellnessGoalsStorage.loadWellnessGoalsForDate(currentDay);
        streakManager = new StreakManager();
        activityGoals = new ActivityGoals(45, 15, 1800); // Default goals


        statsCalculator = new StatisticsCalculator(todayActivities);

        streakManager.checkAllStreaks();
    }


    private void refreshTodayActivities() {
        LocalDate today = LocalDate.now();
        if (!today.equals(currentDay)) {
            currentDay = today;
            this.wellnessGoals = wellnessGoalsStorage.loadWellnessGoalsForDate(currentDay);
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


    public void saveWellnessGoals() {
        wellnessGoalsStorage.saveWellnessGoals(wellnessGoals, currentDay);
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


    public void displayActivityProgress() {
        refreshTodayActivities();
        StatisticsDisplayFormatter formatter = createStatsFormatter();
        formatter.displayActivityProgress();
    }

    public void displayWellnessGoalsProgress() {
        refreshTodayActivities();
        StatisticsDisplayFormatter formatter = createStatsFormatter();
        formatter.displayWellnessGoalsProgress();
    }

    public void displayStreaksInfo() {
        refreshTodayActivities();
        StatisticsDisplayFormatter formatter = createStatsFormatter();
        formatter.displayStreaksAndStats();
    }


    private StatisticsDisplayFormatter createStatsFormatter() {
        return new StatisticsDisplayFormatter(
                wellnessGoals,
                activityGoals,
                streakManager,
                statsCalculator
        );
    }

}
