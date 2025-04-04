class UIManager {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";


    public void displayWelcomeBanner() {
        System.out.println(ANSI_BLUE +
                "                                          \n"+
                "      🌟 WELCOME TO HEALTH TRACKER 🌟  \n" +
                "     Your Personal Wellness Companion   \n" + ANSI_RESET);
        pause(1000);
    }

    public void displayExitBanner() {
        System.out.println(ANSI_GREEN +
                "                                          \n"+
                "       🌟 Stay Healthy, Stay Happy!🌟   \n" +
                "        Your Wellness Journey Matters!    \n" + ANSI_RESET);
    }

    public void displayMainMenu() {
        System.out.println(ANSI_YELLOW+ "❖ DAILY HEALTH TRACKER MENU ❖" + ANSI_RESET);
        System.out.println(ANSI_BLUE +
                "1. 💪 Add a New Activity\n" +
                "2. 📝 Log Activity Progress\n" +
                "3. ✏️ Edit or Delete Activities\n" +
                "4. 🎯 Set Daily Health Goals\n" +
                "5. 📊 View Progress & Statistics\n" +
                "6. 🚪 Exit" + ANSI_RESET);
        System.out.print(ANSI_YELLOW + "➤ Choose an option: " + ANSI_RESET);
    }

    public void clearScreen() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (Exception e) {
            // If clearing screen fails, print multiple newlines
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }

    public void showSuccessMessage(String message) {
        System.out.println(ANSI_GREEN + "✅ " + message + ANSI_RESET);
        pause(1000);
    }

    public void showErrorMessage(String message) {
        System.out.println(ANSI_RED + "❌ " + message + ANSI_RESET);
        pause(1000);
    }

    public void promptEnterToContinue() {
        System.out.println(ANSI_GREEN + "(Press Enter to return to the menu...)" + ANSI_RESET);
    }

    public void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void displayText(String text) {
        System.out.println(text);
    }


    public void displayPrompt(String prompt) {
        System.out.print(ANSI_YELLOW + prompt + ANSI_RESET);
    }
}