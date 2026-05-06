package SmartAI;

import UserManagement.User;

public class AIAnalyzer {

    public static void showInsights(User user) {

        java.lang.System.out.println("\n========== AI INSIGHTS ==========");

        double predicted = predictSpending(user);

        java.lang.System.out.printf("Current Spending: RM %.2f\n", user.totalSpent);

        java.lang.System.out.printf("Predicted Monthly Spending: RM %.2f\n", predicted);

        String highestCategory = highestCategory(user);

        if (highestCategory == null) {
            java.lang.System.out.println("Highest Spending Category: null");
        } else {
            java.lang.System.out.println("Highest Spending Category: " + highestCategory);
        }

        java.lang.System.out.println("\nAI Recommendations:");

        if (user.totalSpent == 0) {

            java.lang.System.out.println("- No spending data available yet.");
            java.lang.System.out.println("- Start adding expenses to receive AI insights.");
            return;
        }

        if (user.food > 300) {

            java.lang.System.out.println("- You spent too much on Food & Drink.");
            java.lang.System.out.println("- Reduce food spending by RM10/day.");
        }

        if (user.shopping < 200) {

            java.lang.System.out.println("- Great job controlling shopping expenses.");
        }

        if (predicted > user.monthlyIncome) {

            java.lang.System.out.println("- Alert: Predicted spending exceeds income.");
        }
    }

    public static double predictSpending(User user) {

        if (user.totalSpent == 0) {
            return 0;
        }

        int currentDay = 15;
        int daysInMonth = 30;

        return (user.totalSpent / currentDay) * daysInMonth;
    }

    public static String highestCategory(User user) {

        if (user.totalSpent == 0) {
            return null;
        }

        if (
                user.food == 0 &&
                user.transport == 0 &&
                user.shopping == 0 &&
                user.entertainment == 0 &&
                user.others == 0
        ) {
            return null;
        }

        double max = user.food;
        String category = "Food & Drink";

        if (user.transport > max) {

            max = user.transport;
            category = "Transport";
        }

        if (user.shopping > max) {

            max = user.shopping;
            category = "Shopping";
        }

        if (user.entertainment > max) {

            max = user.entertainment;
            category = "Entertainment";
        }

        if (user.others > max) {

            category = "Others";
        }

        return category;
    }
}