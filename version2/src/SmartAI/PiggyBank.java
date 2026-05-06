package SmartAI;

import java.util.Scanner;
import UserManagement.User;
import UserManagement.PiggyGoal;

public class PiggyBank {

    static Scanner input = new Scanner(java.lang.System.in);

    public static void showPiggyBank(User user) {

        int choice;

        do {
            java.lang.System.out.println("\n========== PIGGY BANK ==========");

            if (user.piggyGoals.size() == 0) {
                java.lang.System.out.println("Initial Target: null");
                java.lang.System.out.println("No piggy bank goals created yet.");
            } else {
                java.lang.System.out.println("Total Goals: " + user.piggyGoals.size());
                java.lang.System.out.printf("Total Saved In Piggy Bank: RM %.2f\n", getTotalSaved(user));
            }

            java.lang.System.out.println("\n1. View All Goals");
            java.lang.System.out.println("2. Create New Goal");
            java.lang.System.out.println("3. Deposit Money Into Goal");
            java.lang.System.out.println("4. Withdraw From Completed Goal");
            java.lang.System.out.println("5. Back");
            java.lang.System.out.print("Choose: ");

            choice = input.nextInt();

            switch (choice) {

                case 1:
                    viewAllGoals(user);
                    break;

                case 2:
                    createGoal(user);
                    break;

                case 3:
                    deposit(user);
                    break;

                case 4:
                    withdraw(user);
                    break;

                case 5:
                    break;

                default:
                    java.lang.System.out.println("Invalid choice.");
            }

        } while (choice != 5);
    }

    public static void viewAllGoals(User user) {

        if (user.piggyGoals.size() == 0) {
            java.lang.System.out.println("\nNo goals available.");
            java.lang.System.out.println("Target is currently null.");
            return;
        }

        java.lang.System.out.println("\n========== YOUR GOALS ==========");

        for (int i = 0; i < user.piggyGoals.size(); i++) {

            PiggyGoal goal = user.piggyGoals.get(i);

            java.lang.System.out.println("\nGoal " + (i + 1));
            java.lang.System.out.println("Goal Name: " + goal.goalName);
            java.lang.System.out.printf("Saved: RM %.2f / RM %.2f\n",
                    goal.savedAmount,
                    goal.targetAmount);
            java.lang.System.out.printf("Progress: %.1f%%\n", goal.getProgress());

            if (goal.isCompleted()) {
                java.lang.System.out.println("Status: Completed. Withdrawal allowed.");
            } else {
                java.lang.System.out.println("Status: Locked until target is reached.");
            }
        }
    }

    public static void createGoal(User user) {

        input.nextLine();

        java.lang.System.out.println("\n========== CREATE NEW GOAL ==========");

        java.lang.System.out.print("Enter goal name: ");
        String goalName = input.nextLine();

        java.lang.System.out.print("Enter target amount: RM ");
        double targetAmount = input.nextDouble();

        if (targetAmount <= 0) {
            java.lang.System.out.println("Target amount must be more than RM0.");
            return;
        }

        PiggyGoal newGoal = new PiggyGoal(goalName, targetAmount);

        user.piggyGoals.add(newGoal);

        java.lang.System.out.println("New piggy bank goal created successfully.");
    }

    public static void deposit(User user) {

        if (user.piggyGoals.size() == 0) {
            java.lang.System.out.println("\nNo goals available.");
            java.lang.System.out.println("Please create a goal first.");
            return;
        }

        viewAllGoals(user);

        java.lang.System.out.print("\nChoose goal number: ");
        int goalIndex = input.nextInt() - 1;

        if (goalIndex < 0 || goalIndex >= user.piggyGoals.size()) {
            java.lang.System.out.println("Invalid goal number.");
            return;
        }

        PiggyGoal selectedGoal = user.piggyGoals.get(goalIndex);

        java.lang.System.out.print("Enter deposit amount: RM ");
        double amount = input.nextDouble();

        if (amount <= 0) {
            java.lang.System.out.println("Deposit amount must be more than RM0.");
            return;
        }

        if (amount > user.balance) {
            java.lang.System.out.println("Insufficient balance.");
            return;
        }

        user.balance -= amount;
        selectedGoal.savedAmount += amount;

        int pointsEarned = Gamification.calculatePointsFromAmount(amount);
        user.points += pointsEarned;

        java.lang.System.out.println("Money added successfully.");
        java.lang.System.out.println("Goal: " + selectedGoal.goalName);
        java.lang.System.out.printf("Current Saved: RM %.2f / RM %.2f\n",
                selectedGoal.savedAmount,
                selectedGoal.targetAmount);
        java.lang.System.out.println("+" + pointsEarned + " points earned!");
        java.lang.System.out.println("Current Level: " + user.getLevel());
        java.lang.System.out.println("Total Badges: " + user.getBadgeCount());

        Gamification.checkRewardAfterPointsUpdate(user);
    }

    public static void withdraw(User user) {

        if (user.piggyGoals.size() == 0) {
            java.lang.System.out.println("\nNo goals available.");
            return;
        }

        viewAllGoals(user);

        java.lang.System.out.print("\nChoose goal number: ");
        int goalIndex = input.nextInt() - 1;

        if (goalIndex < 0 || goalIndex >= user.piggyGoals.size()) {
            java.lang.System.out.println("Invalid goal number.");
            return;
        }

        PiggyGoal selectedGoal = user.piggyGoals.get(goalIndex);

        if (!selectedGoal.isCompleted()) {
            java.lang.System.out.println("Cannot withdraw.");
            java.lang.System.out.println("Funds are locked until the goal is reached.");
            return;
        }

        user.balance += selectedGoal.savedAmount;

        java.lang.System.out.printf("RM %.2f withdrawn from '%s'.\n",
                selectedGoal.savedAmount,
                selectedGoal.goalName);

        user.piggyGoals.remove(goalIndex);

        java.lang.System.out.println("Goal removed after successful withdrawal.");
    }

    public static double getTotalSaved(User user) {

        double total = 0;

        for (PiggyGoal goal : user.piggyGoals) {
            total += goal.savedAmount;
        }

        return total;
    }
}