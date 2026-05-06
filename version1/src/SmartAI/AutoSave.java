package SmartAI;
import UserManagement.User;

public class AutoSave {

    static double savePercentage = 10;

    public static void showAutoSave(User user) {

        System.out.println("\n===== AUTO SAVE =====");

        double amount = user.income * savePercentage / 100;

        System.out.printf("Current Auto Save: %.0f%%\n", savePercentage);

        System.out.printf("Estimated Save: RM %.2f\n", amount);
    }

    public static void applyAutoSave(User user) {

        double amount = user.income * savePercentage / 100;

        if(user.balance >= amount) {

            user.balance -= amount;

            user.savings += amount;

            System.out.printf("RM %.2f auto saved!\n", amount);
        }
    }
}