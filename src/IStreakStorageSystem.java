import java.util.Map;

interface IStreakStorageSystem {
    void saveStreaks(Map<String, Streak> streaks);
    Map<String, Streak> loadStreaks(Map<String, Streak> defaultStreaks);
}
