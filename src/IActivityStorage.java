import java.time.LocalDate;
import java.util.List;

public interface IActivityStorage {

    boolean saveActivities(List<Activity> activities);
    
    List<Activity> loadAllActivities();

    List<Activity> loadActivitiesForDate(LocalDate date);

    default List<Activity> loadTodayActivities() {
        return loadActivitiesForDate(LocalDate.now());
    }
}

