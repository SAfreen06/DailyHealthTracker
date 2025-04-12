# 🩺 Daily Health Tracker

A console-based Java application to help users build and maintain healthy habits through daily tracking of activities like exercise, meditation, nutrition, hydration, and sleep. The project is inspired by the theme **“Savour the Flavour of Health”**, promoting consistency, self-care, and mindful living.


📌 Features

  - Track multiple health-related activities:
  - Exercise (duration, calories burned)
  - Meditation (duration)
  - Nutrition (meals, calories consumed)
  - Daily wellness (water intake, sleep, screen time, self-care)

- Set daily/weekly goals for each activity
-  Maintain streaks for consistent habits
-  View statistics and progress summaries
-  Save/load data using CSV files
-  Modular OOP design with extensibility in mind
-  Input validation and user-friendly console interface

---

##  Core Classes

| Category | Classes |
|----------|---------|
| **Core Domain** | `Activity`, `ExerciseActivity`, `MeditationActivity`, `NutritionActivity`, `WellnessGoals`, `ActivityGoals` |
| **Application Logic** | `HealthTracker`, `ActivityManager`, `StreakManager`, `StatisticsCalculator`, `MenuManager` |
| **UI** | `UIManager`, `ActivityDisplayFormatter` |
| **Storage** | `CSVActivityStorage`, `CSVWellnessGoalsStorage` |
| **Design Patterns** | `ActivityFactory`, `HealthTrackerApp` (entry point) |

---

## Architecture Highlights

- Uses **OOP principles** like Abstraction, Inheritance, and Encapsulation
- Implements **Factory Design Pattern** for creating activity instances
- Follows **Single Responsibility Principle** for modular class design
- Separates concerns among data management, user interaction, and business logic

---



   


