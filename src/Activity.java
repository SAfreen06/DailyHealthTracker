import java.time.LocalDate;


abstract class Activity {
    protected String name;
    protected String description;
    protected LocalDate date;
    protected boolean completed;

    public Activity(String name, String description) {
        this.name = name;
        this.description = description;
        this.date = LocalDate.now();
        this.completed = false;
    }

    public abstract String getCategory();
    public abstract int getProgress();

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isCompleted() { return completed; }
    public void complete() { this.completed = true; }
}
