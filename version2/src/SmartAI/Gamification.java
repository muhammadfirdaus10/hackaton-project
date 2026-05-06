package SmartAI;

import UserManagement.User;

public class Gamification {

    public static void showGamification(User user) {

        java.lang.System.out.println("\n========== GAMIFICATION ==========");

        java.lang.System.out.println("Current Level: " + user.getLevel());
        java.lang.System.out.println("Total Points: " + user.points);
        java.lang.System.out.println("Points needed for next level: " + user.getPointsToNextLevel());

        java.lang.System.out.println("\n========== BADGES ==========");
        showBadges(user);

        java.lang.System.out.println("\nTotal Badges: " + user.getBadgeCount());

        java.lang.System.out.println("\n========== REWARDS ==========");
        java.lang.System.out.println("Reward rule: 1 reward for every 3 badges.");
        java.lang.System.out.println("Rewards claimed: " + user.rewardsClaimed);
        java.lang.System.out.println("Available rewards: " + user.getAvailableRewardCount());

        if (user.getAvailableRewardCount() > user.rewardsClaimed) {
            java.lang.System.out.println("You have unclaimed rewards.");
            giveReward(user);
        } else {
            java.lang.System.out.println("No new reward available.");
        }

        java.lang.System.out.println("\n========== POINT SYSTEM ==========");
        java.lang.System.out.println("Points earned = 5% of saved or deposited amount.");
        java.lang.System.out.println("Example: Save RM400 = 20 points.");

        java.lang.System.out.println("\n========== LEVEL SYSTEM ==========");
        java.lang.System.out.println("0 - 99 points     = Level 1");
        java.lang.System.out.println("100 - 199 points  = Level 2");
        java.lang.System.out.println("200 - 299 points  = Level 3");
        java.lang.System.out.println("300 - 399 points  = Level 4");
    }

    public static void showBadges(User user) {

        int totalBadges = user.getBadgeCount();

        for (int i = 1; i <= totalBadges; i++) {
            java.lang.System.out.println("- Level " + i + " Badge");
        }
    }

    public static int calculatePointsFromAmount(double amount) {

        return (int) Math.round(amount * 0.05);
    }

    public static void giveReward(User user) {

        while (user.rewardsClaimed < user.getAvailableRewardCount()) {

            double rewardAmount = user.balance * 0.05;

            user.balance += rewardAmount;
            user.rewardsClaimed++;

            java.lang.System.out.println("\nCongratulations!");
            java.lang.System.out.println("You earned a reward for collecting 3 badges.");
            java.lang.System.out.printf("Reward deposited: RM %.2f\n", rewardAmount);
            java.lang.System.out.printf("New Balance: RM %.2f\n", user.balance);
        }
    }

    public static void checkRewardAfterPointsUpdate(User user) {

        if (user.getAvailableRewardCount() > user.rewardsClaimed) {
            giveReward(user);
        }
    }
}