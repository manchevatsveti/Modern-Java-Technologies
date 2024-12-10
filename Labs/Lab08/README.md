```markdown
# Fraud Detector 💳 🕵️‍♂️

### Overview
In today's world, electronic payments are the backbone of the global financial system. More and more, we prefer shopping online, paying with cards, and transferring money electronically. Unfortunately, these transactions are increasingly targeted by malicious actors who exploit security breaches, steal personal data, and use social engineering tactics to commit various types of fraud. This is a significant issue for both financial institutions and individual users. 

Estimates suggest that fraud accounts for about **5% of the global financial turnover**—over **$6 trillion annually**! This drives the need for robust systems and algorithms to reliably detect fraudulent activities and ensure trust in electronic financial operations.

Fraud detection involves risk analysis and identifying suspicious transactions—those deemed "outliers" or anomalies that significantly differ from regular patterns. These anomalies are automatically flagged for human review.

This week, we will leverage file handling, input-output streams, lambda expressions, and the Java Stream API to create **Fraud Detector**—a system that calculates risk by analyzing user financial transactions. The analysis is based on a real dataset containing over 2,500 transactions from Kaggle. The dataset has been pre-processed, and the final version can be downloaded for use in this project.

---

### Dataset
Each line of the dataset contains details of a single transaction in CSV format:
```
TransactionID,AccountID,TransactionAmount,TransactionDate,Location,Channel
```

---

### Implementation Details

#### Transaction Analyzer
In the `bg.sofia.uni.fmi.mjt.frauddetector.analyzer` package, create the `TransactionAnalyzerImpl` class with the following constructor:
```java
public TransactionAnalyzerImpl(Reader reader, List<Rule> rules)
```
This class implements the `TransactionAnalyzer` interface, which provides methods to:
- Retrieve all transactions.
- Fetch all unique account IDs.
- Calculate transaction counts grouped by channel.
- Compute the total amount spent by a user.
- Retrieve all transactions for a specific user.
- Rate the risk associated with a specific account.
- Analyze and rank account risks based on provided rules.

#### Transactions
Model transactions using the `Transaction` record:
```java
public record Transaction(
    String transactionID,
    String accountID,
    double transactionAmount,
    LocalDateTime transactionDate,
    String location,
    Channel channel
)
```
Include a static factory method to parse a CSV line:
```java
public static Transaction of(String line)
```

#### Channels
Transactions occur via one of three channels:
- **ATM**
- **Online**
- **Bank Branch**

Model this using the `Channel` enum:
```java
public enum Channel {
    ATM, ONLINE, BRANCH
}
```

---

### Risk Calculation Rules
Rules evaluate transaction anomalies to calculate account risk. Each rule:
- Has a **threshold** that triggers it.
- Includes a **weight** in the range `[0.0, 1.0]`. The sum of all rule weights must equal `1.0`.

#### Rule Implementations
1. **FrequencyRule**: Flags accounts based on transaction frequency.
   ```java
   public FrequencyRule(int transactionCountThreshold, TemporalAmount timeWindow, double weight)
   ```

2. **LocationsRule**: Flags accounts with transactions from multiple distinct locations.
   ```java
   public LocationsRule(int threshold, double weight)
   ```

3. **SmallTransactionsRule**: Flags accounts with numerous low-value transactions.
   ```java
   public SmallTransactionsRule(int countThreshold, double amountThreshold, double weight)
   ```

4. **ZScoreRule**: Flags accounts with a high Z-score (statistical outlier) for transaction amounts.
   ```java
   public ZScoreRule(double zScoreThreshold, double weight)
   ```

---

### Example Usage
```java
import bg.sofia.uni.fmi.mjt.frauddetector.analyzer.TransactionAnalyzer;
import bg.sofia.uni.fmi.mjt.frauddetector.analyzer.TransactionAnalyzerImpl;
import bg.sofia.uni.fmi.mjt.frauddetector.rule.*;
import java.io.FileReader;
import java.io.Reader;
import java.time.Period;
import java.util.List;

public class Main {
    public static void main(String... args) throws Exception {
        String filePath = "dataset.csv";

        Reader reader = new FileReader(filePath);
        List<Rule> rules = List.of(
            new ZScoreRule(1.5, 0.3),
            new LocationsRule(3, 0.4),
            new FrequencyRule(4, Period.ofWeeks(4), 0.25),
            new SmallTransactionsRule(1, 10.20, 0.05)
        );

        TransactionAnalyzer analyzer = new TransactionAnalyzerImpl(reader, rules);

        System.out.println(analyzer.allAccountIDs());
        System.out.println(analyzer.allTransactionsByUser(analyzer.allTransactions().get(0).accountID()));
        System.out.println(analyzer.accountsRisk());
    }
}
```

---

### Testing
Test your implementation locally with example cases and create unit tests for all classes.

---

### Directory Structure
```
src
└── bg.sofia.uni.fmi.mjt.frauddetector
    ├── analyzer
    │     ├── TransactionAnalyzer.java
    │     ├── TransactionAnalyzerImpl.java  
    ├── rule
    │     ├── FrequencyRule.java
    │     ├── LocationsRule.java
    │     ├── Rule.java
    │     ├── SmallTransactionsRule.java
    │     ├── ZScoreRule.java
    ├── transaction
    │     ├── Channel.java
    │     ├── Transaction.java

test
└── bg.sofia.uni.fmi.mjt.frauddetector
```
