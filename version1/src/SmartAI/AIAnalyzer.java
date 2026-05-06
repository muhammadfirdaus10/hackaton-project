package SmartAI;
import UserManagement.User;

public class AIAnalyzer {

    public static void showInsights(User user) {

        System.out.println("\n===== AI INSIGHTS =====");

        double predicted = predictSpending(user);

        System.out.printf("Predicted Monthly Spending: RM %.2f\n", predicted);

        if(user.food > 300) {

            System.out.println("AI Alert:");
            System.out.println("You spent too much on Food & Drink.");
        }

        if(user.shopping < 200) {

            System.out.println("Positive Trend:");
            System.out.println("Shopping spending is under control.");
        }
    }

    public static double predictSpending(User user) {

        int currentDay = 15;

        return (user.totalSpent / currentDay) * 30;
    }
}