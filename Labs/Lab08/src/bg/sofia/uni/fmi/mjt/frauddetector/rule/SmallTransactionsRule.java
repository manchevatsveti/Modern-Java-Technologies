package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.util.List;

public class SmallTransactionsRule implements Rule {

    int countThreshold;
    double amountThreshold;
    double weight;

    public SmallTransactionsRule(int countThreshold, double amountThreshold, double weight) {
        this.amountThreshold = amountThreshold;
        this.countThreshold = countThreshold;
        this.weight = weight;
    }

    @Override
    public boolean applicable(List<Transaction> transactions) {
        long count = transactions.stream()
            .filter(transaction -> transaction.transactionAmount() <= amountThreshold)
            .count();

        return count >= countThreshold;
    }

    @Override
    public double weight() {
        return weight;
    }
}
