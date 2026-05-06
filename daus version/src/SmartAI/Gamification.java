package SmartAI;
import UserManagement.User;

public class Gamification {

    public static void showGamification(User user) {

        System.out.println("\n===== GAMIFICATION =====");

        System.out.println("Level: " + user.level);

        System.out.println("Points: " + user.points);

        System.out.println("Saving Streak: " + user.streak + " days");

        if(user.streak >= 7) {

            System.out.println("Reward Unlocked!");
        }
    }
}