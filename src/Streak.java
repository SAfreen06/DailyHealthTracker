import java.time.LocalDate;

class Streak {
    private String category;
    private int currentStreak;
    private int bestStreak;
    private LocalDate lastCompletedDate;

    public Streak(String category) {
        this.category = category;
        this.currentStreak = 0;
        this.bestStreak = 0;
        this.lastCompletedDate = null; // No activities completed yet
    }

    public Streak(String category, int currentStreak, int bestStreak, LocalDate lastCompletedDate) {
        this.category = category;
        this.currentStreak = currentStreak;
        this.bestStreak = bestStreak;
        this.lastCompletedDate = lastCompletedDate;
    }

    public void updateStreak(LocalDate activityDate) {
        if (lastCompletedDate == null) {
            currentStreak = 1;
            lastCompletedDate = activityDate;
        } else if (activityDate.equals(lastCompletedDate)) {

        } else if (activityDate.equals(lastCompletedDate.plusDays(1))) {
            currentStreak++;
            lastCompletedDate = activityDate;

            if (currentStreak > bestStreak) {
                bestStreak = currentStreak;
            }
        } else if (activityDate.isAfter(lastCompletedDate.plusDays(1))) {
            currentStreak = 1;
            lastCompletedDate = activityDate;
        }
    }


    public void checkStreakStatus(LocalDate currentDate) {
        if (lastCompletedDate != null &&
                currentDate.isAfter(lastCompletedDate.plusDays(1))) {
            currentStreak = 0;
        }
    }

    // Getters
    public String getCategory() { return category; }
    public int getCurrentStreak() { return currentStreak; }
    public int getBestStreak() { return bestStreak; }
    public LocalDate getLastCompletedDate() { return lastCompletedDate; }

    @Override
    public String toString() {
        return category + "," + currentStreak + "," + bestStreak + "," +
                (lastCompletedDate != null ? lastCompletedDate.toString() : "null");
    }
}
