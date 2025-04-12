class NutritionActivity extends Activity {
    private int caloriesConsumed;
    private int mealsLogged;

    public NutritionActivity(String name, String description, int caloriesConsumed, int mealsLogged) {
        super(name, description);
        this.caloriesConsumed = caloriesConsumed;
        this.mealsLogged = mealsLogged;
    }

    @Override
    public String getCategory() { return "Nutrition"; }

    @Override
    public int getProgress() { return caloriesConsumed; }

    public int getMealsLogged() { return mealsLogged; }
}
