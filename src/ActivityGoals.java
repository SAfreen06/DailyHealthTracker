class ActivityGoals {
    private int exerciseGoal;
    private int meditationGoal;
    private int nutritionGoal;

    public ActivityGoals(int exerciseGoal, int meditationGoal, int nutritionGoal) {
        this.exerciseGoal = exerciseGoal;
        this.meditationGoal = meditationGoal;
        this.nutritionGoal = nutritionGoal;
    }

    public int getExerciseGoal() {
        return exerciseGoal;
    }

    public void setExerciseGoal(int goal) {
        this.exerciseGoal = goal;
    }

    public int getMeditationGoal() {
        return meditationGoal;
    }

    public void setMeditationGoal(int goal) {
        this.meditationGoal = goal;
    }

    public int getNutritionGoal() {
        return nutritionGoal;
    }

    public void setNutritionGoal(int goal) {
        this.nutritionGoal = goal;
    }
}

