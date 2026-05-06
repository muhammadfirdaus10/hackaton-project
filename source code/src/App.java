// ============================================================
//  App.java
//  Entry point for the GXBank Financial Advisor simulation.
//
//  Flow:
//    1. Create a sample user (fresh graduate)
//    2. Add budget rules
//    3. Add savings goals
//    4. Add transaction history
//    5. Run AI Advisor analysis
//    6. Predict monthly spending
//    7. Generate savings & debt plans
//    8. Simulate a real-time purchase nudge
//    9. Show credit improvement tips
//   10. Run gamification (XP, badges, missions, leaderboard)
// ============================================================

import java.util.List;

public class App {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║         GXBank AI Financial Advisor v1.0         ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        // --------------------------------------------------------
        // 1. Create user
        // --------------------------------------------------------
        User user = new User(
                "USR001",
                "Ahmad Fariz",
                "fariz@email.com",
                "5e884898da28047151d0e56f8dc629277a", // simulated hash
                "fresh_graduate",
                3500.00 // RM 3,500 / month
        );
        user.balance = 1200.00;
        user.creditScore = 620;
        user.totalDebt = 8000.00; // e.g. PTPTN + credit card

        System.out.println("👤 User created: " + user.name +
                " | Income: RM " + user.monthlyIncome +
                " | Credit Score: " + user.creditScore);

        // --------------------------------------------------------
        // 2. Set up budget rules
        // --------------------------------------------------------
        user.budgetRules.add(new BudgetRule("Food", 600.00));
        user.budgetRules.add(new BudgetRule("Transport", 300.00));
        user.budgetRules.add(new BudgetRule("Entertainment", 200.00));
        user.budgetRules.add(new BudgetRule("Shopping", 250.00));
        user.budgetRules.add(new BudgetRule("Utilities", 150.00));

        System.out.println("📋 Budget rules set: " + user.budgetRules.size() + " categories\n");

        // --------------------------------------------------------
        // 3. Set up savings goals
        // --------------------------------------------------------
        long now = System.currentTimeMillis() / 1000L;

        user.goals.add(new SavingsGoal(
                "Emergency Fund",
                10500.00, // 3 months expenses target
                8800.00, // already saved – 83.8%, triggers goal-gradient nudge
                0,
                false));
        user.goals.add(new SavingsGoal(
                "Laptop Upgrade",
                2500.00,
                500.00,
                now + (60L * 86400L), // deadline: 60 days from now
                false));
        user.goals.add(new SavingsGoal(
                "Holiday Trip",
                1500.00,
                1500.00, // completed goal
                0,
                true));

        System.out.println("🎯 Savings goals added: " + user.goals.size() + " goals");

        // --------------------------------------------------------
        // 4. Add transaction history (last 30 days)
        // --------------------------------------------------------
        long day = 86400L;

        // Food
        user.transactions.add(new Transaction("T001", "Food", -45.50, now - day, "Lunch at mamak"));
        user.transactions.add(new Transaction("T002", "Food", -120.00, now - 3 * day, "Weekly groceries"));
        user.transactions.add(new Transaction("T003", "Food", -67.00, now - 7 * day, "Dinner with friends"));
        user.transactions.add(new Transaction("T004", "Food", -85.00, now - 12 * day, "Groceries"));
        user.transactions.add(new Transaction("T005", "Food", -55.00, now - 15 * day, "Lunch + tapau"));
        user.transactions.add(new Transaction("T006", "Food", -200.00, now - 18 * day, "Family dinner"));
        // Food total = RM 572.50 — close to RM 600 limit (95%), triggers WARNING

        // Transport
        user.transactions.add(new Transaction("T007", "Transport", -120.00, now - 2 * day, "Grab rides"));
        user.transactions.add(new Transaction("T008", "Transport", -80.00, now - 10 * day, "Petrol"));
        user.transactions.add(new Transaction("T009", "Transport", -60.00, now - 20 * day, "Touch n Go reload"));
        // Transport total = RM 260.00 — within RM 300 limit

        // Entertainment — OVER budget
        user.transactions.add(new Transaction("T010", "Entertainment", -90.00, now - 5 * day, "Cinema tickets"));
        user.transactions
                .add(new Transaction("T011", "Entertainment", -75.00, now - 8 * day, "Streaming subscriptions"));
        user.transactions.add(new Transaction("T012", "Entertainment", -80.00, now - 14 * day, "Bowling + karaoke"));
        // Entertainment total = RM 245.00 — exceeds RM 200 limit (over budget!)

        // Shopping
        user.transactions.add(new Transaction("T013", "Shopping", -150.00, now - 6 * day, "Shoes"));
        user.transactions.add(new Transaction("T014", "Shopping", -60.00, now - 22 * day, "Clothes"));
        // Shopping total = RM 210.00 — within RM 250 limit

        // Utilities
        user.transactions
                .add(new Transaction("T015", "Utilities", -110.00, now - 25 * day, "Electricity + water bill"));
        // Utilities total = RM 110.00 — within RM 150 limit

        // Income credit
        user.transactions.add(new Transaction("T016", "Income", +3500.00, now - 30 * day, "Monthly salary"));

        user.streakDays = 9; // 9-day streak — triggers streak praise + Week Warrior badge

        System.out.println("💳 Transactions loaded: " + user.transactions.size() + " records");
        System.out.printf("   Monthly expenses so far: RM %.2f%n", user.getMonthlyExpenses());
        System.out.printf("   Savings rate: %.0f%%%n%n", user.getSavingsRate() * 100);

        // --------------------------------------------------------
        // 5. AI Advisor — analyse user
        // --------------------------------------------------------
        AIAdvisor advisor = new AIAdvisor();

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🤖 AI INSIGHTS");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        List<AIAdvisor.Insight> insights = advisor.analyzeUser(user);
        for (AIAdvisor.Insight insight : insights) {
            System.out.println("\n[" + insight.type.toUpperCase() + " | Priority " + insight.priority + "]");
            System.out.println("  " + insight.title);
            System.out.println("  " + insight.message);
        }

        // --------------------------------------------------------
        // 6. Spending predictions
        // --------------------------------------------------------
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("📈 MONTHLY SPENDING PREDICTIONS");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        List<AIAdvisor.SpendingPrediction> predictions = advisor.predictMonthlySpending(user);
        System.out.printf("%-16s %12s %12s %10s%n", "Category", "Predicted", "Budget", "Status");
        System.out.println("─".repeat(54));
        for (AIAdvisor.SpendingPrediction p : predictions) {
            System.out.printf("%-16s  RM %8.2f  RM %8.2f  %s%n",
                    p.category,
                    p.predictedAmount,
                    p.budgetLimit,
                    p.willExceed ? "🔴 EXCEED" : "✅ OK");
        }

        // --------------------------------------------------------
        // 7. Savings & debt plans
        // --------------------------------------------------------
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("💡 PERSONALISED SAVINGS PLAN");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println(advisor.generateSavingsPlan(user));

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🏦 DEBT REDUCTION PLAN");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println(advisor.generateDebtReductionPlan(user));

        System.out.printf("💰 Suggested monthly savings amount: RM %.2f%n%n",
                advisor.suggestSavingsAmount(user));

        // --------------------------------------------------------
        // 8. Real-time purchase nudge
        // --------------------------------------------------------
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🛒 REAL-TIME NUDGE SIMULATION");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        // Scenario A: affordable food purchase
        Transaction pendingFood = new Transaction("P001", "Food", -30.00, now, "Nasi lemak + teh tarik");
        System.out.println("Pending: " + pendingFood.description + " (RM 30.00)");
        System.out.println(advisor.getRealTimeNudge(user, pendingFood));

        // Scenario B: entertainment purchase that busts the budget
        Transaction pendingEntertain = new Transaction("P002", "Entertainment", -60.00, now, "Concert tickets");
        System.out.println("\nPending: " + pendingEntertain.description + " (RM 60.00)");
        System.out.println(advisor.getRealTimeNudge(user, pendingEntertain));

        // Scenario C: no budget set category
        Transaction pendingHealth = new Transaction("P003", "Healthcare", -50.00, now, "Clinic visit");
        System.out.println("\nPending: " + pendingHealth.description + " (RM 50.00)");
        System.out.println(advisor.getRealTimeNudge(user, pendingHealth));

        // --------------------------------------------------------
        // 9. Credit improvement tips
        // --------------------------------------------------------
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("⭐ CREDIT SCORE IMPROVEMENT TIPS  (Score: " + user.creditScore + ")");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        List<String> tips = advisor.getCreditImprovementTips(user);
        for (String tip : tips) {
            System.out.println("  " + tip);
        }

        // --------------------------------------------------------
        // 10. Gamification
        // --------------------------------------------------------
        Gamification game = new Gamification();

        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🎮 GAMIFICATION ENGINE");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        // Award XP for actions
        game.awardXP(user, 50, "Logged daily transactions");
        game.awardXP(user, 30, "Stayed under budget (Transport)");
        game.awardXP(user, 75, "Deposited into Emergency Fund goal");

        // Check and unlock badges
        System.out.println("\n  Checking badge eligibility...");
        game.checkAndAwardBadges(user);

        // Daily missions
        System.out.println("\n  📅 Daily Missions:");
        List<Gamification.Mission> missions = game.getDailyMissions(user);
        for (Gamification.Mission m : missions) {
            String status = m.completed ? "✅" : "⬜";
            System.out.printf("  %s [+%d XP] %s – %s%n",
                    status, m.xpReward, m.title, m.description);
        }

        // Complete an available mission manually
        game.completeMission(user, "m3"); // "Check Your Insights"

        // Add to leaderboard and print
        game.addToLeaderboard(user);

        // Add a couple of simulated friends for leaderboard context
        User friend1 = new User("USR002", "Nurul Ain", "", "", "fresh_graduate", 3000);
        friend1.xpPoints = 430;
        User friend2 = new User("USR003", "Wei Jian", "", "", "student", 1500);
        friend2.xpPoints = 890;
        game.addToLeaderboard(friend1);
        game.addToLeaderboard(friend2);

        System.out.println("\n  🏆 Leaderboard:");
        System.out.printf("  %-4s %-16s %8s  %s%n", "Rank", "Name", "XP", "Level");
        System.out.println("  " + "─".repeat(44));
        int rank = 1;
        for (Gamification.LeaderboardEntry entry : game.getLeaderboard()) {
            System.out.printf("  #%-3d %-16s %8d  %s%n",
                    rank++, entry.userName, entry.xpPoints, entry.level);
        }

        // Final stats card
        game.printUserStats(user);
        game.rewardSmartSpending(user);

        System.out.println("\n✅ Simulation complete.\n");

    }
}