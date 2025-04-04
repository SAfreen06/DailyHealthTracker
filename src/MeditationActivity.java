class MeditationActivity extends Activity {
    private int durationMinutes;

    public MeditationActivity(String name, String description, int durationMinutes) {
        super(name, description);
        this.durationMinutes = durationMinutes;
    }

    @Override
    public String getCategory() { return "Meditation"; }

    @Override
    public int getProgress() { return durationMinutes; }
}
