// ============================================================
//  AIAdvisor.java
//  Behavioural-economics-inspired AI engine.
//
//  Principles applied:
//    1. Mental accounting  – bucket spending into named pots
//    2. Loss aversion      – frame overspending as a "loss"
//    3. Default nudges     – auto-enroll in smart-save rounds
//    4. Goal gradient      – show % progress to keep motivation
//    5. Commitment device  – lock savings for N days
// ============================================================

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AIAdvisor {

    // ============================================================
    //  Inner data classes
    // ============================================================

    public static class Insight {
        public String type;       // "warning" | "tip" | "praise" | "nudge"
        public String title;
        public String message;
        public int    priority;   // 1 (high) – 5 (low)

        public Insight(String type, String title, String message, int priority) {
            this.type     = type;
            this.title    = title;
            this.message  = message;
            this.priority = priority;
        }
    }

    public static class SpendingPrediction {
        public String  category;
        public double  predictedAmount;
        public double  budgetLimit;
        public boolean willExceed;

        public SpendingPrediction(String category, double predictedAmount,
                                  double budgetLimit, boolean willExceed) {
            this.category        = category;
            this.predictedAmount = predictedAmount;
            this.budgetLimit     = budgetLimit;
            this.willExceed      = willExceed;
        }
    }

    // ============================================================
    //  Private helpers
    // ============================================================

    private double calcDebtToIncomeRatio(User u) {
        if (u.monthlyIncome <= 0) return 99.0;
        return u.totalDebt / (u.monthlyIncome * 12.0);
    }

    /** Returns a composite savings behaviour score from 0 to 100. */
    private int scoreSavingsBehaviour(User u) {
        int score = 0;

        // Savings rate: up to 50 pts (25% savings = full marks)
        double sr = u.getSavingsRate();
        score += (int) Math.min(sr * 200.0, 50.0);

        // Streak: up to 30 pts
        score += Math.min(u.streakDays * 2, 30);

        // Completed goals: up to 20 pts
        int completedGoals = 0;
        for (SavingsGoal g : u.goals) {
            if (g.completed) completedGoals++;
        }
        score += Math.min(completedGoals * 5, 20);

        return score;
    }

    private String spendingTrendLabel(User u, String cat) {
        double spent = u.getCategorySpending(cat);
        for (BudgetRule rule : u.budgetRules) {
            if (rule.category.equals(cat)) {
                double ratio = spent / rule.monthlyLimit;
                if (ratio > 1.0)  return "OVER_BUDGET";
                if (ratio > 0.85) return "WARNING";
                if (ratio > 0.5)  return "ON_TRACK";
                return "HEALTHY";
            }
        }
        return "NO_BUDGET_SET";
    }

    // ============================================================
    //  Core analysis
    // ============================================================

    public List<Insight> analyzeUser(User u) {
        List<Insight> insights = new ArrayList<>();

        // 1. Debt-to-income ratio check
        double dti = calcDebtToIncomeRatio(u);
        if (dti > 0.43) {
            insights.add(new Insight("warning",
                "⚠ High Debt Load Detected",
                "Your debt-to-income ratio is " + (int)(dti * 100) +
                "%. Financial advisors recommend keeping it below 43%. " +
                "Consider the debt snowball plan in your dashboard.",
                1));
        }

        // 2. Savings rate nudge (loss-aversion framing)
        double sr = u.getSavingsRate();
        if (sr < 0.10) {
            insights.add(new Insight("nudge",
                "💸 You're Leaving Money on the Table",
                "Saving less than 10% of income means you could be missing " +
                "RM " + (int)(u.monthlyIncome * 0.10) +
                " in wealth-building each month. Small leaks sink big ships.",
                1));
        } else if (sr >= 0.20) {
            insights.add(new Insight("praise",
                "🏆 Savings Champion!",
                "You're saving " + (int)(sr * 100) +
                "% of your income. That's top 20% of GXBank youth users!",
                4));
        }

        // 3. Over-budget categories
        for (BudgetRule rule : u.budgetRules) {
            if (u.isOverBudget(rule.category)) {
                double over = u.getCategorySpending(rule.category) - rule.monthlyLimit;
                insights.add(new Insight("warning",
                    "🔴 " + rule.category + " Budget Exceeded",
                    "You've spent RM " + (int) over +
                    " more than your " + rule.category +
                    " budget this month. Tap to see where it went.",
                    2));
            }
        }

        // 4. Streak praise
        if (u.streakDays >= 7) {
            insights.add(new Insight("praise",
                "🔥 " + u.streakDays + "-Day Streak!",
                "You've stayed within budget for " + u.streakDays +
                " days straight. Keep it up!",
                3));
        }

        // 5. Goal gradient – near completion nudge
        for (SavingsGoal g : u.goals) {
            if (!g.completed && g.targetAmount > 0) {
                double pct = g.savedAmount / g.targetAmount;
                if (pct >= 0.8 && pct < 1.0) {
                    insights.add(new Insight("nudge",
                        "🎯 Almost There – " + g.name,
                        "You're " + (int)(pct * 100) +
                        "% of the way to your '" + g.name +
                        "' goal. Just RM " +
                        (int)(g.targetAmount - g.savedAmount) + " left!",
                        2));
                }
            }
        }

        // Sort by priority (1 = highest)
        insights.sort(Comparator.comparingInt(i -> i.priority));
        return insights;
    }

    public List<SpendingPrediction> predictMonthlySpending(User u) {
        // Simple linear projection: scale current spend to end of month.
        // (In a real system this would use ML regression on historical data.)
        List<SpendingPrediction> preds = new ArrayList<>();
        long now        = System.currentTimeMillis() / 1000L;
        int  dayOfMonth = (int)((now / 86400L) % 30) + 1;
        double scaleFactor = (dayOfMonth > 0) ? (30.0 / dayOfMonth) : 1.0;

        for (BudgetRule rule : u.budgetRules) {
            double spent     = u.getCategorySpending(rule.category);
            double predicted = spent * scaleFactor;
            preds.add(new SpendingPrediction(
                rule.category, predicted, rule.monthlyLimit,
                predicted > rule.monthlyLimit));
        }
        return preds;
    }

    // ============================================================
    //  Personalised recommendations
    // ============================================================

    public String generateSavingsPlan(User u) {
        double income  = u.monthlyIncome;
        double needs   = income * 0.50;
        double wants   = income * 0.30;
        double savings = income * 0.20;

        StringBuilder sb = new StringBuilder();
        sb.append("=== Personalised 50-30-20 Savings Plan for ").append(u.name).append(" ===\n\n");
        sb.append(String.format("Monthly Income : RM %.2f%n%n", income));
        sb.append(String.format("[ NEEDS  – 50%% ]  RM %.2f  (rent, groceries, transport)%n", needs));
        sb.append(String.format("[ WANTS  – 30%% ]  RM %.2f  (dining, entertainment, shopping)%n", wants));
        sb.append(String.format("[ SAVINGS– 20%% ]  RM %.2f  (emergency fund → investments)%n%n", savings));

        sb.append("Priority order for savings bucket:\n");
        sb.append(String.format("  1. Emergency fund   → RM %.0f (3 months expenses)%n", income * 3));
        sb.append("  2. High-interest debt payoff\n");
        sb.append("  3. Short-term goals (gadgets, travel)\n");
        sb.append("  4. Long-term wealth (unit trust, EPF top-up)\n");

        if ("student".equals(u.profileType)) {
            sb.append("\n[Student tip] Even RM 50/month invested in an index fund\n");
            sb.append("  compounds to ~RM 30,000 by age 35 at 8% p.a.\n");
        }
        return sb.toString();
    }

    public String generateDebtReductionPlan(User u) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Debt Reduction Plan ===\n\n");
        sb.append(String.format("Total Debt    : RM %.2f%n", u.totalDebt));
        sb.append(String.format("Monthly Income: RM %.2f%n", u.monthlyIncome));
        sb.append(String.format("DTI Ratio     : %d%%%n%n",
            (int)(calcDebtToIncomeRatio(u) * 100)));

        double available = u.monthlyIncome * 0.15;
        sb.append(String.format("Recommended monthly debt payment: RM %.2f%n", available));
        double months = (available > 0) ? u.totalDebt / available : 9999;
        sb.append(String.format("Estimated payoff period         : %d months%n%n", (int) months));

        sb.append("Strategy: AVALANCHE METHOD\n");
        sb.append("  → Pay minimums on all debts, then throw extra cash at\n");
        sb.append("    the highest-interest debt first. Saves the most money.\n\n");
        sb.append("Automatic micro-payment: GXBank will round up every\n");
        sb.append("  purchase to the nearest RM 1 and apply the change\n");
        sb.append("  to your debt balance.\n");
        return sb.toString();
    }

    public double suggestSavingsAmount(User u) {
        double disposable = u.monthlyIncome - u.getMonthlyExpenses();
        double suggested  = u.monthlyIncome * 0.20;
        return Math.min(suggested, disposable * 0.80);
    }

    // ============================================================
    //  Nudge engine
    // ============================================================

    public String getRealTimeNudge(User u, Transaction pending) {
        double catSpend = u.getCategorySpending(pending.category) + (-pending.amount);

        for (BudgetRule rule : u.budgetRules) {
            if (rule.category.equals(pending.category)) {
                double pct = catSpend / rule.monthlyLimit * 100.0;
                if (pct > 100) {
                    return String.format(
                        "🚨 STOP: This purchase would put you %.0f RM over your %s budget!%n" +
                        "   Consider: pay from next month's allowance, or skip.",
                        catSpend - rule.monthlyLimit, pending.category);
                } else if (pct > 80) {
                    return String.format(
                        "⚠ Heads up: After this, you'll have used %.0f%% of your %s " +
                        "budget with days still left this month.", pct, pending.category);
                } else {
                    return String.format(
                        "✅ You're within budget (%.0f%% used). Approved!", pct);
                }
            }
        }
        return "ℹ No budget set for '" + pending.category +
               "'. Consider adding one in Settings → Budgets.";
    }

    // ============================================================
    //  Credit score coaching
    // ============================================================

    public List<String> getCreditImprovementTips(User u) {
        List<String> tips = new ArrayList<>();
        if (u.creditScore < 600) {
            tips.add("🔴 Score <600: Pay ALL bills on time for 6 months – " +
                     "payment history is 35% of your score.");
        }
        if (u.totalDebt > u.monthlyIncome * 3) {
            tips.add("🟠 High debt: Reduce total debt below 3× monthly " +
                     "income to improve score significantly.");
        }
        if (u.creditScore < 700) {
            tips.add("🟡 Keep credit card utilisation below 30% of limit.");
        }
        tips.add("🟢 GXBank Tip: Use GXBank Debit for daily spending – " +
                 "zero risk to credit score, full spending visibility.");
        tips.add("📈 Check your CCRIS report free at BNM at least once a year.");
        return tips;
    }
}