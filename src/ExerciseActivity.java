class ExerciseActivity extends Activity {
    private int caloriesBurnt;
    private int durationMinutes;

    public ExerciseActivity(String name, String description, int caloriesBurnt, int durationMinutes) {
        super(name, description);
        this.caloriesBurnt = caloriesBurnt;
        this.durationMinutes = durationMinutes;
    }

    @Override
    public String getCategory() { return "Exercise"; }

    @Override
    public int getProgress() { return durationMinutes; }

    public int getCaloriesBurnt() { return caloriesBurnt; }

    public int getDuration() {
        return durationMinutes;
    }
}
