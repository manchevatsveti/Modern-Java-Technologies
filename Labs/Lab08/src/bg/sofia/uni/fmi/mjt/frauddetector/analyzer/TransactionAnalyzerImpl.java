package bg.sofia.uni.fmi.mjt.frauddetector.analyzer;

import bg.sofia.uni.fmi.mjt.frauddetector.rule.Rule;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Channel;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TransactionAnalyzerImpl implements TransactionAnalyzer {

    private final List<Transaction> transactions;
    private final List<Rule> rules;

    public TransactionAnalyzerImpl(Reader reader, List<Rule> rules) {
        if (reader == null) {
            throw new IllegalArgumentException("Reader cannot be null.");
        }
        if (rules == null || rules.isEmpty()) {
            throw new IllegalArgumentException("Rules cannot be null or empty.");
        }

        var buffReader = new BufferedReader((reader));
        try {
            buffReader.readLine(); //read the first line
        } catch (IOException e) {
            e.fillInStackTrace();
        }
        transactions = buffReader.lines().map(Transaction::of).toList();

        this.rules = rules;

        validateRules();
    }

    private void validateRules() {
        double sum = rules.stream()
            .mapToDouble(Rule::weight)
            .sum();

        if (Double.compare(sum, 1.0) != 0) {
            throw new IllegalArgumentException("Rules sum up to more than 1.0.");
        }
    }

    @Override
    public List<Transaction> allTransactions() {
        return transactions;
    }

    @Override
    public List<String> allAccountIDs() {
        return transactions.stream()
            .map(Transaction::accountID)
            .distinct()
            .toList();
    }

    @Override
    public Map<Channel, Integer> transactionCountByChannel() {
        Map<Channel, Long> res =
            transactions.stream()
                .collect(Collectors.groupingBy(Transaction::channel, Collectors.counting()));

        return res.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().intValue()
            ));
    }

    @Override
    public double amountSpentByUser(String accountID) {
        if ( accountID == null || accountID.isEmpty()) {
            throw new IllegalArgumentException("AccountId is empty or null.");
        }

        return transactions.stream()
            .filter(transaction -> transaction.accountID().equalsIgnoreCase(accountID))
            .mapToDouble(Transaction::transactionAmount)
            .sum();

    }

    @Override
    public List<Transaction> allTransactionsByUser(String accountId) {
        if ( accountId == null || accountId.isEmpty()) {
            throw new IllegalArgumentException("AccountId is empty or null.");
        }

        return transactions.stream()
            .filter(transaction -> transaction.accountID().equalsIgnoreCase(accountId))
            .toList();
    }

    @Override
    public double accountRating(String accountId) {
        if ( accountId == null || accountId.isEmpty()) {
            throw new IllegalArgumentException("AccountId is empty or null.");
        }

        List<Transaction> transactionList = allTransactionsByUser(accountId);

        return rules.stream()
            .filter(r -> r.applicable(transactionList))
            .mapToDouble(Rule::weight)
            .sum();

    }

    @Override
    public SortedMap<String, Double> accountsRisk() {
        List<String> accountIds = allAccountIDs();

        return accountIds.stream()
            .collect(Collectors.toMap(
                accountId -> accountId,
                this::accountRating,
                (existing, replacement) -> existing,
                TreeMap::new
            ));
    }
}
