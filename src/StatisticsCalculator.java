import java.util.List;

class StatisticsCalculator {
    private List<Activity> activities;

    public StatisticsCalculator(List<Activity> activities) {
        this.activities = activities;
    }

    public int calculateCategoryProgress(String category) {
        int totalProgress = 0;
        for (Activity activity : activities) {
            if (activity.getCategory().equals(category) && activity.isCompleted()) {
                totalProgress += activity.getProgress();
            }
        }
        return totalProgress;
    }

    public int calculateTotalCaloriesBurned() {
        int totalCalories = 0;
        for (Activity activity : activities) {
            if (activity instanceof ExerciseActivity && activity.isCompleted()) {
                ExerciseActivity exerciseActivity = (ExerciseActivity) activity;
                totalCalories += exerciseActivity.getCaloriesBurnt();
            }
        }
        return totalCalories;
    }

    public int calculateTotalMeditationMinutes() {
        int totalMinutes = 0;
        for (Activity activity : activities) {
            if (activity instanceof MeditationActivity && activity.isCompleted()) {
                MeditationActivity meditationActivity = (MeditationActivity) activity;
                totalMinutes += meditationActivity.getProgress();
            }
        }
        return totalMinutes;
    }

    public int calculateTotalMealsLogged() {
        int totalMeals = 0;
        for (Activity activity : activities) {
            if (activity instanceof NutritionActivity && activity.isCompleted()) {
                NutritionActivity nutritionActivity = (NutritionActivity) activity;
                totalMeals += nutritionActivity.getMealsLogged();
            }
        }
        return totalMeals;
    }
}