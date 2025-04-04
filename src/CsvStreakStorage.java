import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

class CsvStreakStorage implements IStreakStorageSystem {
    private final String filePath;

    public CsvStreakStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void saveStreaks(Map<String, Streak> streaks) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Streak streak : streaks.values()) {
                writer.println(streak.toString());
            }
        } catch (IOException e) {
            System.err.println("Error saving streaks: " + e.getMessage());
        }
    }

    @Override
    public Map<String, Streak> loadStreaks(Map<String, Streak> defaultStreaks) {
        Map<String, Streak> loadedStreaks = new HashMap<>(defaultStreaks);
        File file = new File(filePath);

        if (!file.exists()) {
            return loadedStreaks;
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

                    loadedStreaks.put(category, new Streak(category, currentStreak, bestStreak, lastDate));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading streaks: " + e.getMessage());
        }

        return loadedStreaks;
    }
}