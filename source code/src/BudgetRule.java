// ============================================================
//  BudgetRule.java
//  Represents a monthly spending limit for a category.
// ============================================================

public class BudgetRule {
    public String category;
    public double monthlyLimit;   // MYR

    public BudgetRule() {}

    public BudgetRule(String category, double monthlyLimit) {
        this.category     = category;
        this.monthlyLimit = monthlyLimit;
    }
}
