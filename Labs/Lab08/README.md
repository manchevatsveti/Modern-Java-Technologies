```markdown
# Fraud Detector 💳 🕵️‍♂️

In today's world, electronic payments are the backbone of the global financial system. Increasingly, we prefer online shopping, card payments, and electronic money transfers. Unfortunately, electronic financial flows have become a frequent target for malicious actors who exploit security breaches, steal personal data, and employ social engineering to commit fraud. This is a pressing issue for both financial institutions and individual users. Fraud is estimated to cause global losses of about 5% of the world's financial turnover, exceeding $6 trillion annually!

This necessitates the development of sophisticated systems and algorithms to detect fraud and ensure the security and trustworthiness of electronic financial operations.

## About Fraud Detection

Fraud detection involves analyzing risks and identifying suspicious financial transactions—those that deviate significantly from normal patterns. These outliers (anomalies) are automatically flagged for detailed human review.

### Key Features
This week, we'll apply our knowledge of file handling, I/O streams, lambda expressions, and Java Stream APIs to create a **Fraud Detector** that calculates risks by analyzing users' financial transactions. We'll use a real dataset from Kaggle, containing data for over 2,500 financial transactions. The dataset includes fields like:

- `TransactionID`
- `AccountID`
- `TransactionAmount`
- `TransactionDate`
- `Location`
- `Channel`

### Transaction Analyzer
The `TransactionAnalyzer` interface and its implementation (`TransactionAnalyzerImpl`) process the dataset to analyze and retrieve various insights.

#### Interface Methods
- **`List<Transaction> allTransactions()`**: Retrieve all transactions.
- **`List<String> allAccountIDs()`**: Get all unique account IDs.
- **`Map<Channel, Integer> transactionCountByChannel()`**: Group transactions by their channel.
- **`double amountSpentByUser(String accountID)`**: Calculate the total amount spent by a user.
- **`List<Transaction> allTransactionsByUser(String accountId)`**: Retrieve transactions for a specific account.
- **`double accountRating(String accountId)`**: Get the risk rating for an account.
- **`SortedMap<String, Double> accountsRisk()`**: Calculate risk scores for all accounts, sorted in descending order.

### Rules for Risk Calculation
Fraud detection is based on rules applied to transaction data. Each rule evaluates risk based on anomalies and has a threshold and weight. The sum of all rule weights must equal 1.0.

#### Examples
- **`FrequencyRule`**: Evaluates risk based on transaction frequency within a time window.
- **`LocationsRule`**: Evaluates risk based on the number of distinct transaction locations.
- **`SmallTransactionsRule`**: Flags accounts with excessive small transactions.
- **`ZScoreRule`**: Evaluates risk using the Z-score of transaction amounts.

### Implementation Highlights
1. **Transaction Modeling**: Transactions are represented by the `Transaction` record, parsed from the dataset using a factory method.
2. **Channels**: Transactions can occur via `ATM`, `ONLINE`, or `BRANCH`, represented by the `Channel` enum.

### Example Usage
```java
import bg.sofia.uni.fmi.mjt.frauddetector.analyzer.*;
import bg.sofia.uni.fmi.mjt.frauddetector.rule.*;
import java.io.*;
import java.time.*;
import java.util.*;

public class Main {
    public static void main(String... args) throws FileNotFoundException {
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
        System.out.println(analyzer.allTransactionsByUser("account1"));
        System.out.println(analyzer.accountsRisk());
    }
}
```

### Testing
- First, test locally with sample data.
- Then, create **unit tests** for all components.

### Project Structure
```plaintext
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
     └── ...
```
```
