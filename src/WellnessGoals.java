class WellnessGoals {
    private int waterIntake;
    private int sleepDuration;
    private int selfCareTime;
    private int screenTime;

    public void displayGoals() {
        System.out.println("-------------------------------------------------");
        System.out.println("| #  | Activity Name    | Goal               | Intake/Duration |");
        System.out.println("-------------------------------------------------");
        System.out.println("| 1  | Water Intake    | 8 glasses          | _ _             |");
        System.out.println("| 2  | Sleep Duration  | 8 hours            | _ _             |");
        System.out.println("| 3  | Self Care       | 30 minutes         | _ _             |");
        System.out.println("| 4  | Screen Time     | 2 hours or less    | _ _             |");
        System.out.println("-------------------------------------------------");
    }


    public void setWaterIntake(int waterIntake) { this.waterIntake = waterIntake; }
    public void setSleepDuration(int sleepDuration) { this.sleepDuration = sleepDuration; }
    public void setSelfCareTime(int selfCareTime) { this.selfCareTime = selfCareTime; }
    public void setScreenTime(int screenTime) { this.screenTime = screenTime; }

    public int getWaterIntake() { return waterIntake; }
    public int getSleepDuration() { return sleepDuration; }
    public int getSelfCareTime() { return selfCareTime; }
    public int getScreenTime() { return screenTime; }
}

