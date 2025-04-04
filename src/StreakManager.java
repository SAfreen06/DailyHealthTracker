import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

class StreakManager {
    private Map<String, Streak> streaks;
    private final IStreakStorageSystem storage;

    public StreakManager(IStreakStorageSystem storage) {
        this.storage = storage;

        Map<String, Streak> defaultStreaks = new HashMap<>();
        defaultStreaks.put("Exercise", new Streak("Exercise"));
        defaultStreaks.put("Meditation", new Streak("Meditation"));
        defaultStreaks.put("Nutrition", new Streak("Nutrition"));


        this.streaks = storage.loadStreaks(defaultStreaks);
    }


    public StreakManager() {
        this(new CsvStreakStorage("streaks.csv"));
    }

    public void recordActivity(String category, LocalDate date) {
        ensureCategoryExists(category);
        streaks.get(category).updateStreak(date);
        storage.saveStreaks(streaks);
    }


    public void recordActivity(String category) {
        recordActivity(category, LocalDate.now());
    }

    public void checkAllStreaks() {
        LocalDate today = LocalDate.now();
        for (Streak streak : streaks.values()) {
            streak.checkStreakStatus(today);
        }
        storage.saveStreaks(streaks);
    }

    private void ensureCategoryExists(String category) {
        if (!streaks.containsKey(category)) {
            streaks.put(category, new Streak(category));
        }
    }

    public int getCurrentStreak(String category) {
        return streaks.containsKey(category) ? streaks.get(category).getCurrentStreak() : 0;
    }

    public int getBestStreak(String category) {
        return streaks.containsKey(category) ? streaks.get(category).getBestStreak() : 0;
    }

    public Map<String, Streak> getAllStreaks() {
        return streaks;
    }

    public void addCategory(String category) {
        if (!streaks.containsKey(category)) {
            streaks.put(category, new Streak(category));
            storage.saveStreaks(streaks);
        }
    }

    public boolean removeCategory(String category) {
        if (streaks.containsKey(category)) {
            streaks.remove(category);
            storage.saveStreaks(streaks);
            return true;
        }
        return false;
    }
}