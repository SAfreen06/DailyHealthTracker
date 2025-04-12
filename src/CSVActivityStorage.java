import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CSVActivityStorage implements IActivityStorage {
    private static final String DEFAULT_FILENAME = "activities.csv";
    private final String filename;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public CSVActivityStorage() {
        this(DEFAULT_FILENAME);
    }

    public CSVActivityStorage(String filename) {
        this.filename = filename;
    }

    @Override
    public boolean saveActivities(List<Activity> activities) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {

            writer.println("type,name,description,date,completed,param1,param2");


            for (Activity activity : activities) {
                String type = activity.getClass().getSimpleName();
                String name = escape(activity.getName());
                String description = escape(activity.getDescription());
                String date = activity.getDate().format(DATE_FORMATTER);
                String completed = String.valueOf(activity.isCompleted());

                String param1 = "0";
                String param2 = "0";

                if (activity instanceof ExerciseActivity) {
                    ExerciseActivity exercise = (ExerciseActivity) activity;
                    param1 = String.valueOf(exercise.getCaloriesBurnt());
                    param2 = String.valueOf(exercise.getDuration());
                } else if (activity instanceof MeditationActivity) {
                    MeditationActivity meditation = (MeditationActivity) activity;
                    param1 = String.valueOf(meditation.getDuration());
                } else if (activity instanceof NutritionActivity) {
                    NutritionActivity nutrition = (NutritionActivity) activity;
                    param1 = String.valueOf(nutrition.getProgress());
                    param2 = String.valueOf(nutrition.getMealsLogged());
                }

                writer.println(String.join(",", type, name, description, date, completed, param1, param2));
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error saving activities: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Activity> loadAllActivities() {
        List<Activity> activities = new ArrayList<>();
        File file = new File(filename);

        if (!file.exists()) {
            return activities;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                Activity activity = parseActivityFromCsv(line);
                if (activity != null) {
                    activities.add(activity);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading activities: " + e.getMessage());
        }

        return activities;
    }

    @Override
    public List<Activity> loadActivitiesForDate(LocalDate date) {
        return loadAllActivities().stream()
                .filter(activity -> activity.getDate().equals(date))
                .collect(Collectors.toList());
    }

    private Activity parseActivityFromCsv(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length < 7) return null; // Skip malformed lines

            String type = parts[0];
            String name = unescape(parts[1]);
            String description = unescape(parts[2]);
            LocalDate date = LocalDate.parse(parts[3], DATE_FORMATTER);
            boolean completed = Boolean.parseBoolean(parts[4]);
            int param1 = Integer.parseInt(parts[5]);
            int param2 = Integer.parseInt(parts[6]);

            Activity activity = null;

            switch (type) {
                case "ExerciseActivity":
                    activity = ActivityFactory.createExerciseActivity(name, description, param1, param2);
                    break;
                case "MeditationActivity":
                    activity = ActivityFactory.createMeditationActivity(name, description, param1);
                    break;
                case "NutritionActivity":
                    activity = ActivityFactory.createNutritionActivity(name, description, param1, param2);
                    break;
                default:
                    return null;
            }


            if (activity != null) {
                activity.date = date;
                if (completed) {
                    activity.complete();
                }
                return activity;
            }
        } catch (Exception e) {

            System.err.println("Error parsing activity: " + e.getMessage());
        }
        return null;
    }


    private String escape(String text) {
        return text.replace(",", "\\,");
    }

    private String unescape(String text) {
        return text.replace("\\,", ",");
    }
}