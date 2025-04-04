import java.util.List;

class ActivityDisplayFormatter {
    public static void displayBasicActivities(List<Activity> activities) {
        System.out.println("-------------------------------------------------");
        System.out.println("| #  | Activity Name | Category   | Status      |");
        System.out.println("-------------------------------------------------");
        for (int i = 0; i < activities.size(); i++) {
            Activity activity = activities.get(i);
            System.out.printf("| %-2d | %-13s | %-10s | %-10s |\n",
                    i + 1,
                    activity.getName(),
                    activity.getCategory(),
                    activity.isCompleted() ? "Completed" : "Pending");
        }
        System.out.println("-------------------------------------------------");
    }

    public static void displayDetailedActivities(List<Activity> activities) {
        System.out.println("-------------------------------------------------");
        System.out.println("| #  | Activity Name | Category   | Description | Status |");
        System.out.println("-------------------------------------------------");
        for (int i = 0; i < activities.size(); i++) {
            Activity activity = activities.get(i);
            System.out.printf("| %-2d | %-13s | %-10s | %-10s | %-8s |\n",
                    i + 1,
                    activity.getName(),
                    activity.getCategory(),
                    activity.getDescription(),
                    activity.isCompleted() ? "Completed" : "Pending");
        }
        System.out.println("-------------------------------------------------");
    }
}
