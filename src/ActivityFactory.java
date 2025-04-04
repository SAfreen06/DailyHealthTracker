
public class ActivityFactory {

    public static ExerciseActivity createExerciseActivity(String name, String description,
                                                          int caloriesBurnt, int durationMinutes) {
        return new ExerciseActivity(name, description, caloriesBurnt, durationMinutes);
    }

    public static MeditationActivity createMeditationActivity(String name, String description,
                                                              int durationMinutes) {
        return new MeditationActivity(name, description, durationMinutes);
    }

    public static NutritionActivity createNutritionActivity(String name, String description,
                                                            int caloriesConsumed, int mealsLogged) {
        return new NutritionActivity(name, description, caloriesConsumed, mealsLogged);
    }


    public static Activity createActivityByCategory(String category, String name, String description) {
        switch (category.toLowerCase()) {
            case "exercise":
                return createExerciseActivity(name, description, 0, 0);

            case "meditation":
                return createMeditationActivity(name, description, 0);

            case "nutrition":
                return createNutritionActivity(name, description, 0, 0);

            default:
                throw new IllegalArgumentException("Unknown activity category: " + category);
        }
    }
}
