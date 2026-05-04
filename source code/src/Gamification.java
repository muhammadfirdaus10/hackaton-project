// ============================================================
//  Gamification.java
//  Applies game-design mechanics to reinforce positive habits.
//
//  Features:
//   • XP points for every good financial action
//   • Level system  (Rookie → Pro → Master → Legend)
//   • Badges / achievements
//   • Leaderboard among friends (simulated)
//   • Daily challenge missions
// ============================================================

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Gamification {

    // ============================================================
    //  Inner data classes
    // ============================================================

    public static class Badge {
        public String  id;
        public String  name;
        public String  description;
        public String  emoji;
        public boolean earned;

        public Badge(String id, String name, String description,
                     String emoji, boolean earned) {
            this.id          = id;
            this.name        = name;
            this.description = description;
            this.emoji       = emoji;
            this.earned      = earned;
        }
    }

    public static class Mission {
        public String  id;
        public String  title;
        public String  description;
        public int     xpReward;
        public boolean completed;

        public Mission(String id, String title, String description,
                       int xpReward, boolean completed) {
            this.id          = id;
            this.title       = title;
            this.description = description;
            this.xpReward    = xpReward;
            this.completed   = completed;
        }
    }

    public static class LeaderboardEntry {
        public String userName;
        public int    xpPoints;
        public String level;

        public LeaderboardEntry(String userName, int xpPoints, String level) {
            this.userName = userName;
            this.xpPoints = xpPoints;
            this.level    = level;
        }
    }

    // ============================================================
    //  Fields
    // ============================================================

    private List<Badge>            badges      = new ArrayList<>();
    private List<LeaderboardEntry> leaderboard = new ArrayList<>();

    // ============================================================
    //  Constructor
    // ============================================================

    public Gamification() {
        initBadges();
    }

    private void initBadges() {
        badges.add(new Badge("first_save",    "First Step",    "Made your first savings deposit",    "🥇", false));
        badges.add(new Badge("goal_complete", "Goal Crusher",  "Completed a savings goal",           "🎯", false));
        badges.add(new Badge("streak_7",      "Week Warrior",  "7-day budget streak",                "🔥", false));
        badges.add(new Badge("streak_30",     "Month Master",  "30-day budget streak",               "💎", false));
        badges.add(new Badge("debt_free",     "Debt Slayer",   "Reduced debt to zero",               "⚔️", false));
        badges.add(new Badge("saver_10",      "10% Club",      "Saved 10% of income for a month",    "💰", false));
        badges.add(new Badge("saver_20",      "20% Club",      "Saved 20% of income for a month",    "🏆", false));
        badges.add(new Badge("no_overspend",  "Budget Ninja",  "Zero over-budget categories",        "🥷", false));
        badges.add(new Badge("early_bird",    "Early Bird",    "Set up auto-save within first week", "🐦", false));
        badges.add(new Badge("credit_hero",   "Credit Hero",   "Credit score above 700",             "⭐", false));
    }

    // ============================================================
    //  XP & Levels
    // ============================================================

    public void awardXP(User user, int xp, String reason) {
        user.xpPoints += xp;
        System.out.printf("  ++%d XP  [%s]  (Total: %d XP – %s)%n",
            xp, reason, user.xpPoints, getLevel(user.xpPoints));
    }

    public String getLevel(int xp) {
        if (xp >= 5000) return "💎 Legend";
        if (xp >= 2000) return "🏅 Master";
        if (xp >= 750)  return "🥈 Pro";
        if (xp >= 200)  return "🥉 Advanced";
        return "🌱 Rookie";
    }

    public int xpToNextLevel(int xp) {
        if (xp < 200)  return 200  - xp;
        if (xp < 750)  return 750  - xp;
        if (xp < 2000) return 2000 - xp;
        if (xp < 5000) return 5000 - xp;
        return 0;   // already Legend
    }

    // ============================================================
    //  Badges
    // ============================================================

    public void checkAndAwardBadges(User user) {
        for (Badge b : badges) {
            if (b.earned) continue;

            boolean earn = false;
            switch (b.id) {
                case "first_save":
                    earn = !user.goals.isEmpty() && user.goals.get(0).savedAmount > 0;
                    break;
                case "goal_complete":
                    for (SavingsGoal g : user.goals) if (g.completed) earn = true;
                    break;
                case "streak_7":
                    earn = user.streakDays >= 7;
                    break;
                case "streak_30":
                    earn = user.streakDays >= 30;
                    break;
                case "debt_free":
                    earn = user.totalDebt <= 0;
                    break;
                case "saver_10":
                    earn = user.getSavingsRate() >= 0.10;
                    break;
                case "saver_20":
                    earn = user.getSavingsRate() >= 0.20;
                    break;
                case "credit_hero":
                    earn = user.creditScore >= 700;
                    break;
            }

            if (earn) {
                b.earned      = true;
                user.badge    = b.name;
                user.xpPoints += 100;   // badge bonus
                System.out.printf("  🏅 BADGE UNLOCKED: %s %s – %s (+100 XP)%n",
                    b.emoji, b.name, b.description);
            }
        }
    }

    public List<Badge> getAllBadges() {
        return new ArrayList<>(badges);
    }

    // ============================================================
    //  Missions
    // ============================================================

    public List<Mission> getDailyMissions(User user) {
        List<Mission> missions = new ArrayList<>();
        missions.add(new Mission("m1", "Stay in Budget Today",   "Don't exceed any budget category",        50, false));
        missions.add(new Mission("m2", "Log Every Expense",      "Record 3 or more transactions today",     30, false));
        missions.add(new Mission("m3", "Check Your Insights",    "View your AI insights dashboard",         20, false));
        missions.add(new Mission("m4", "Save Something Today",   "Add any amount to a savings goal",        40, false));
        missions.add(new Mission("m5", "Review Your Goals",      "Open the goals screen and check progress",15, false));

        // Mark completed based on user state (simplified)
        if (user.streakDays > 0)               missions.get(0).completed = true;
        if (user.transactions.size() >= 3)     missions.get(1).completed = true;  // Fix: was user.Transactions

        return missions;
    }

    public void completeMission(User user, String missionId) {
        for (Mission m : getDailyMissions(user)) {
            if (m.id.equals(missionId) && !m.completed) {
                awardXP(user, m.xpReward, "Mission: " + m.title);
                break;
            }
        }
    }

    // ============================================================
    //  Leaderboard
    // ============================================================

    public void addToLeaderboard(User user) {
        leaderboard.add(new LeaderboardEntry(
            user.name, user.xpPoints, getLevel(user.xpPoints)));
        leaderboard.sort((a, b) -> Integer.compare(b.xpPoints, a.xpPoints));
    }

    public List<LeaderboardEntry> getLeaderboard() {
        return new ArrayList<>(leaderboard);
    }

    // ============================================================
    //  Display
    // ============================================================

    public void printUserStats(User user) {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.printf( "║     %-28s║%n", user.name);
        System.out.println("╠══════════════════════════════════╣");
        System.out.printf( "║ Level   : %-23s║%n", getLevel(user.xpPoints));
        System.out.printf( "║ XP      : %-23d║%n", user.xpPoints);
        System.out.printf( "║ To next : %-23d║%n", xpToNextLevel(user.xpPoints));
        System.out.printf( "║ Streak  : %-20d days  ║%n", user.streakDays);
        System.out.printf( "║ Badge   : %-23s║%n",
            user.badge.isEmpty() ? "None yet" : user.badge);
        System.out.println("╚══════════════════════════════════╝");
    }
}