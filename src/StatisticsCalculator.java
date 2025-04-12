import java.time.LocalDate;
import java.util.List;

class StatisticsCalculator {
    private List<Activity> activities;

    public StatisticsCalculator(List<Activity> activities) {
        this.activities = activities;
    }

    public void updateActivities(List<Activity> activities) {
        this.activities = activities;
    }
    public int calculateCategoryProgress(String category) {
        LocalDate today = LocalDate.now();
        int totalProgress = 0;

        for (Activity activity : activities) {
            if (activity.getCategory().equals(category) &&
                    activity.isCompleted() &&
                    activity.getDate().equals(today)) {
                totalProgress += activity.getProgress();
            }
        }

        return totalProgress;
    }

    public int calculateTotalCaloriesBurned() {
        LocalDate today = LocalDate.now();
        int totalCalories = 0;

        for (Activity activity : activities) {
            if (activity.getCategory().equals("Exercise") &&
                    activity.isCompleted() &&
                    activity.getDate().equals(today) &&
                    activity instanceof ExerciseActivity) {
                totalCalories += ((ExerciseActivity) activity).getCaloriesBurnt();
            }
        }

        return totalCalories;
    }

    public int calculateTotalMeditationMinutes() {
        LocalDate today = LocalDate.now();
        int totalMinutes = 0;

        for (Activity activity : activities) {
            if (activity.getCategory().equals("Meditation") &&
                    activity.isCompleted() &&
                    activity.getDate().equals(today) &&
                    activity instanceof MeditationActivity) {
                totalMinutes += ((MeditationActivity) activity).getDuration();
            }
        }

        return totalMinutes;
    }

    public int calculateTotalMealsLogged() {
        LocalDate today = LocalDate.now();
        int totalMeals = 0;

        for (Activity activity : activities) {
            if (activity.getCategory().equals("Nutrition") &&
                    activity.isCompleted() &&
                    activity.getDate().equals(today)) {
                totalMeals++;
            }
        }

        return totalMeals;
    }
}