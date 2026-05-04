// ============================================================
//  Transaction.java
//  Represents a single financial transaction for a GXBank user.
// ============================================================

public class Transaction {

    public String transactionId;
    public String category;     // e.g. "Food", "Transport", "Entertainment"
    public double amount;       // negative = expense, positive = income/deposit
    public long   timestamp;    // Unix epoch seconds
    public String description;

    public Transaction() {}

    public Transaction(String transactionId, String category,
                       double amount, long timestamp, String description) {
        this.transactionId = transactionId;
        this.category      = category;
        this.amount        = amount;
        this.timestamp     = timestamp;
        this.description   = description;
    }
}