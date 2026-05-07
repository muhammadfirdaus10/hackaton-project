package SmartAI;

import java.util.Scanner;
import UserManagement.User;

public class AutoSave {

    static Scanner input = new Scanner(java.lang.System.in);

    public static void showAutoSave(User user) {

        int choice;

        do {

            java.lang.System.out.println("\n========== AUTO SAVE ==========");

            java.lang.System.out.println("Status: "
                    + (user.autoSaveOn ? "ON" : "OFF"));

            java.lang.System.out.printf("Save Percentage: %.1f%%\n",
                    user.autoSavePercentage);

            java.lang.System.out.printf("Savings Account: RM %.2f\n",
                    user.savings);

            java.lang.System.out.println("\n1. Toggle Auto Save");
            java.lang.System.out.println("2. Change Save Percentage");
            java.lang.System.out.println("3. Apply Auto Save");
            java.lang.System.out.println("4. Back");

            java.lang.System.out.print("Choose: ");
            choice = input.nextInt();

            switch(choice) {

                case 1:

                    user.autoSaveOn = !user.autoSaveOn;

                    java.lang.System.out.println("Auto Save updated.");
                    break;

                case 2:

                    java.lang.System.out.print("Enter percentage: ");
                    user.autoSavePercentage = input.nextDouble();

                    java.lang.System.out.println("Percentage updated.");
                    break;

                case 3:

                    applyAutoSave(user);
                    break;

                case 4:
                    break;

                default:

                    java.lang.System.out.println("Invalid choice.");
            }

        } while(choice != 4);
    }

    public static void applyAutoSave(User user) {

        if(!user.autoSaveOn) {

            java.lang.System.out.println("Auto Save is OFF.");
            return;
        }

        double amount = user.balance * user.autoSavePercentage / 100;

        if (amount <= user.balance) {

            user.balance -= amount;
            user.savings += amount;

            int pointsEarned = Gamification.calculatePointsFromAmount(amount);
            user.points += pointsEarned;

            java.lang.System.out.printf("RM %.2f auto saved successfully!\n", amount);

            java.lang.System.out.println("+" + pointsEarned + " points earned!");
            java.lang.System.out.println("Current Level: " + user.getLevel());
            java.lang.System.out.println("Total Badges: " + user.getBadgeCount());

            Gamification.checkRewardAfterPointsUpdate(user);
        }

        else {

            java.lang.System.out.println("Insufficient balance.");
        }
    }
}