import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class CSVWellnessGoalsStorage implements IWellnessGoalsStorage {
    private static final String DEFAULT_FILENAME = "wellness_goals.csv";
    private final String filename;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public CSVWellnessGoalsStorage() {
        this(DEFAULT_FILENAME);
    }

    public CSVWellnessGoalsStorage(String filename) {
        this.filename = filename;
    }

    @Override
    public boolean saveWellnessGoals(WellnessGoals goals, LocalDate date) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            // Check if we need to update an existing entry or add a new one
            Map<LocalDate, WellnessGoals> allGoals = loadAllWellnessGoals();
            allGoals.put(date, goals);

            // Clear the file and write all entries
            writer.close(); // Close to reopen in non-append mode
            try (PrintWriter clearWriter = new PrintWriter(new FileWriter(filename))) {
                clearWriter.println("date,waterIntake,sleepDuration,selfCareTime,screenTime");

                for (Map.Entry<LocalDate, WellnessGoals> entry : allGoals.entrySet()) {
                    LocalDate entryDate = entry.getKey();
                    WellnessGoals entryGoals = entry.getValue();

                    clearWriter.println(String.join(",",
                            entryDate.format(DATE_FORMATTER),
                            String.valueOf(entryGoals.getWaterIntake()),
                            String.valueOf(entryGoals.getSleepDuration()),
                            String.valueOf(entryGoals.getSelfCareTime()),
                            String.valueOf(entryGoals.getScreenTime())
                    ));
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error saving wellness goals: " + e.getMessage());
            return false;
        }
    }

    @Override
    public WellnessGoals loadWellnessGoalsForDate(LocalDate date) {
        Map<LocalDate, WellnessGoals> allGoals = loadAllWellnessGoals();
        return allGoals.getOrDefault(date, new WellnessGoals());
    }

    @Override
    public Map<LocalDate, WellnessGoals> loadAllWellnessGoals() {
        Map<LocalDate, WellnessGoals> allGoals = new HashMap<>();
        File file = new File(filename);

        if (!file.exists()) {
            return allGoals;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Skip header line
            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    if (parts.length < 5) continue; // Skip malformed lines

                    LocalDate entryDate = LocalDate.parse(parts[0], DATE_FORMATTER);
                    int waterIntake = Integer.parseInt(parts[1]);
                    int sleepDuration = Integer.parseInt(parts[2]);
                    int selfCareTime = Integer.parseInt(parts[3]);
                    int screenTime = Integer.parseInt(parts[4]);

                    WellnessGoals goals = new WellnessGoals();
                    goals.setWaterIntake(waterIntake);
                    goals.setSleepDuration(sleepDuration);
                    goals.setSelfCareTime(selfCareTime);
                    goals.setScreenTime(screenTime);

                    allGoals.put(entryDate, goals);
                } catch (Exception e) {
                    System.err.println("Error parsing wellness goal entry: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading wellness goals: " + e.getMessage());
        }

        return allGoals;
    }
}
