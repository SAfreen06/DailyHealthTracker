import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

class StreakManager {
    private Map<String, Streak> streaks;
    private static final String STREAK_FILE = "streaks.csv";

    public StreakManager() {
        streaks = new HashMap<>();
        streaks.put("Exercise", new Streak("Exercise"));
        streaks.put("Meditation", new Streak("Meditation"));
        streaks.put("Nutrition", new Streak("Nutrition"));

        loadStreaksFromFile();
    }


    public void updateStreak(String category, LocalDate date) {
        if (!streaks.containsKey(category)) {
            streaks.put(category, new Streak(category));
        }

        streaks.get(category).updateStreak(date);
        saveStreaksToFile();
    }


    public void checkAllStreaks() {
        LocalDate today = LocalDate.now();
        for (Streak streak : streaks.values()) {
            streak.checkStreakStatus(today);
        }
        saveStreaksToFile();
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


    private void saveStreaksToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STREAK_FILE))) {
            for (Streak streak : streaks.values()) {
                writer.println(streak.toString());
            }
        } catch (IOException e) {
            System.err.println("Error saving streaks: " + e.getMessage());
        }
    }


    private void loadStreaksFromFile() {
        File file = new File(STREAK_FILE);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String category = parts[0];
                    int currentStreak = Integer.parseInt(parts[1]);
                    int bestStreak = Integer.parseInt(parts[2]);
                    LocalDate lastDate = parts[3].equals("null") ? null : LocalDate.parse(parts[3]);

                    streaks.put(category, new Streak(category, currentStreak, bestStreak, lastDate));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading streaks: " + e.getMessage());
        }
    }
}
