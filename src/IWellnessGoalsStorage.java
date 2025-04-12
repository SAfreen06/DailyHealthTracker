import java.time.LocalDate;
import java.util.Map;

public interface IWellnessGoalsStorage {

    boolean saveWellnessGoals(WellnessGoals goals, LocalDate date);


    WellnessGoals loadWellnessGoalsForDate(LocalDate date);
    
    Map<LocalDate, WellnessGoals> loadAllWellnessGoals();
}