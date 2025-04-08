class WellnessGoals {
    private int waterIntake;
    private int sleepDuration;
    private int selfCareTime;
    private int screenTime;


    private static final String[] GOAL_NAMES = {"Water Intake", "Sleep Duration", "Self-Care Time", "Screen Time"};
    private static final String[] GOAL_ICONS = {"💧", "😴", "🌿", "📱"};
    private static final String[] GOAL_UNITS = {"glasses", "hours", "minutes", "hours"};
    private static final String[] GOAL_TIPS = {
            "\u001B[34mHealth tip: Aim for 8 glasses (2L) of water daily for proper hydration\u001B[0m",
            "\u001B[34mHealth tip: 7-9 hours is recommended for adults for optimal rest\u001B[0m",
            "\u001B[34mHealth tip: Even 10-15 minutes of self-care daily can reduce stress\u001B[0m",
            "\u001B[34mHealth tip: Try to limit recreational screen time to 2 hours per day\u001B[0m"
    };
    private static final int[] RECOMMENDED_VALUES = {8, 8, 30, 2};
    private static final int[] MIN_VALUES = {0, 0, 0, 0};
    private static final int[] MAX_VALUES = {20, 24, 180, 24};

    public void displayGoalOptions(UIManager uiManager) {
        for (int i = 0; i < GOAL_NAMES.length; i++) {
            uiManager.displayText((i+1) + ". " + GOAL_ICONS[i] + " " + GOAL_NAMES[i] +
                    " (Current: " + getGoalValue(i) + " " + GOAL_UNITS[i] + ")");
        }
    }

    public String getGoalName(int index) {
        if (index >= 0 && index < GOAL_NAMES.length) {
            return GOAL_NAMES[index];
        }
        return "";
    }

    public String getGoalUnit(int index) {
        if (index >= 0 && index < GOAL_UNITS.length) {
            return GOAL_UNITS[index];
        }
        return "";
    }

    public String getGoalTip(int index) {
        if (index >= 0 && index < GOAL_TIPS.length) {
            return GOAL_TIPS[index];
        }
        return "";
    }

    public int getRecommendedValue(int index) {
        if (index >= 0 && index < RECOMMENDED_VALUES.length) {
            return RECOMMENDED_VALUES[index];
        }
        return 0;
    }

    public boolean isValidGoalIndex(int index) {
        return index >= 0 && index < GOAL_NAMES.length;
    }

    public boolean isValidGoalValue(int index, int value) {
        if (index >= 0 && index < MIN_VALUES.length && index < MAX_VALUES.length) {
            return value >= MIN_VALUES[index] && value <= MAX_VALUES[index];
        }
        return false;
    }

    public int getMinValue(int index) {
        if (index >= 0 && index < MIN_VALUES.length) {
            return MIN_VALUES[index];
        }
        return 0;
    }

    public int getMaxValue(int index) {
        if (index >= 0 && index < MAX_VALUES.length) {
            return MAX_VALUES[index];
        }
        return 0;
    }

    public int getGoalValue(int index) {
        switch (index) {
            case 0: return waterIntake;
            case 1: return sleepDuration;
            case 2: return selfCareTime;
            case 3: return screenTime;
            default: return 0;
        }
    }

    public boolean updateGoalValue(int index, int value) {
        if (!isValidGoalValue(index, value)) {
            return false;
        }

        switch (index) {
            case 0: setWaterIntake(value); break;
            case 1: setSleepDuration(value); break;
            case 2: setSelfCareTime(value); break;
            case 3: setScreenTime(value); break;
            default: return false;
        }
        return true;
    }

    public String getProgressFeedback(int index, int value) {

        if (index == 3) {
            if (value <= RECOMMENDED_VALUES[index]) {
                return "Great job keeping screen time low!";
            } else if (value <= RECOMMENDED_VALUES[index] * 1.5) {
                return "Try to reduce screen time when possible.";
            } else {
                return "Consider setting limits on screen usage.";
            }
        }

        else {
            if (value >= RECOMMENDED_VALUES[index]) {
                return "Excellent progress on your " + GOAL_NAMES[index] + "!";
            } else if (value >= RECOMMENDED_VALUES[index] * 0.7) {
                return "Good effort! Keep working toward your goal.";
            } else {
                return "Try to increase this value tomorrow.";
            }
        }
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