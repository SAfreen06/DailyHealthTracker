class MeditationActivity extends Activity {
    private int duration; // in minutes

    public MeditationActivity(String name, String description, int duration) {
        super(name, description);
        this.duration = duration;
    }

    @Override
    public String getCategory() {
        return "Meditation";
    }

    @Override
    public int getProgress() {
        return duration;
    }

    public int getDuration() {
        return duration;
    }
}

